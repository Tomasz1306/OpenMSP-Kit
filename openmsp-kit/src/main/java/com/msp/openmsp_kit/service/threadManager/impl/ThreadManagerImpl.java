package com.msp.openmsp_kit.service.threadManager.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.common.Priority;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.domain.task.Task;
import com.msp.openmsp_kit.service.database.DatabaseManager;
import com.msp.openmsp_kit.service.file.FileManager;
import com.msp.openmsp_kit.service.metrics.MetricsCollector;
import com.msp.openmsp_kit.service.rateLimiter.impl.RateLimiterImpl;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import com.msp.openmsp_kit.service.threadManager.ThreadManager;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ThreadManagerImpl implements ThreadManager {

    private final TaskExecutor taskExecutor;
    private final RateLimiterImpl rateLimiter;
    private final DatabaseManager databaseManager;
    private final FileManager fileManager;
    private final MetricsCollector metricsCollector;

    private enum Status {
        RUNNING, PAUSED, STOPPED, ERROR
    }

    private final BlockingQueue<Result<?>> databaseQueueHighPriority;
    private final BlockingQueue<Result<?>> databaseQueueMediumPriority;
    private final BlockingQueue<Result<?>> databaseQueueLowPriority;
    private final BlockingQueue<Result<?>> fileQueue;

    private final ExecutorService downloaderService;

    private final ExecutorService databaseExecutorService;
    private final ExecutorService fileExecutorService;

    private final Object lock = new Object();
    private Status status;

    ThreadManagerImpl(TaskExecutor taskExecutor,
                      RateLimiterImpl rateLimiter,
                      DatabaseManager databaseManager,
                      FileManager fileManager,
                      MetricsCollector metricsCollector) {
        status = Status.RUNNING;

        this.taskExecutor = taskExecutor;
        this.rateLimiter = rateLimiter;
        this.metricsCollector = metricsCollector;

        databaseExecutorService = Executors.newVirtualThreadPerTaskExecutor();
        fileExecutorService = Executors.newVirtualThreadPerTaskExecutor();
        downloaderService = Executors.newFixedThreadPool(2);

        databaseQueueHighPriority = new LinkedBlockingQueue<>(2000);
        databaseQueueMediumPriority = new LinkedBlockingQueue<>(2000);
        databaseQueueLowPriority = new LinkedBlockingQueue<>(2000);

        fileQueue = new LinkedBlockingQueue<>(5000);
        this.databaseManager = databaseManager;
        this.fileManager = fileManager;
    }

    @Override
    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    @Override
    public boolean isPaused() {
        return status == Status.PAUSED;
    }

    @Override
    public void pause() {
        if (status == Status.PAUSED) {
            return;
        }
        status = Status.PAUSED;
    }

    @Override
    public void start() {
        synchronized (lock) {
            if (status == Status.STOPPED) {
                status = Status.RUNNING;
                runThreads();
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {
        synchronized (lock) {
            if (status == Status.RUNNING) {
                status = Status.STOPPED;
                stopThreads();
            }
        }
    }

    @Override
    public void runThreads() {
        CompletableFuture.runAsync(this::runDatabaseThread);
        CompletableFuture.runAsync(this::runFileThread);
    }

    @Override
    public void stopThreads() {
        databaseExecutorService.shutdown();
        fileExecutorService.shutdown();
        downloaderService.shutdown();
    }

    @Override
    public void processBatches(List<List<Task>> batches) {
        if (!isRunning()) {
            System.out.println("ThreadManager is not running");
            return;
        }
        int i = 0;
        for (List<Task> batch : batches) {
            System.out.println("BATCH " + i++ + " of " + batches.size());
            processBatch(batch);
        }
    }

    @Override
    public void processBatch(List<Task> batch) {
        metricsCollector.incrementTotalBatchesProcessed();
        long startTime = System.currentTimeMillis();
        List<Result<?>> results = batch
                .stream()
                .map(task -> {
                    return CompletableFuture.supplyAsync(() -> {
                        rateLimiter.acquire();
                        var result = taskExecutor.process(task);
                        metricsCollector.incrementProcessedTasks();
                        return result;
                    }, downloaderService);
                })
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        metricsCollector.incrementFailedTasks();
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .toList();

        long endTime = System.currentTimeMillis();
        Duration duration = Duration.ofMillis(endTime - startTime);

        long seconds = duration.toSeconds();
        long millis = duration.toMillisPart();

        System.out.printf("RESULTS (size %d) processing time: %d s %d ms%n", results.size(), seconds, millis);

        for (Result<?> result : results) {
            try {
                if (result.priority().equals(Priority.HIGH)) {
                    databaseQueueHighPriority.put(result);
                } else if (result.priority().equals(Priority.MEDIUM)) {
                    databaseQueueMediumPriority.put(result);
                } else if (result.priority().equals(Priority.LOW)) {
                    databaseQueueLowPriority.put(result);
                }
                if (hasImageToDownload(result)) {
                    metricsCollector.setfileQueueSize(fileQueue.size());
                    fileQueue.put(result);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void runDatabaseThread() {
        while (isRunning() || (
                            !databaseQueueHighPriority.isEmpty() &&
                            !databaseQueueMediumPriority.isEmpty() &&
                            !databaseQueueLowPriority.isEmpty())) {
            try {
                Result<?> result;
                if (!databaseQueueHighPriority.isEmpty()) {
                    result =  databaseQueueHighPriority.take();
                } else if (!databaseQueueMediumPriority.isEmpty()) {
                    result =  databaseQueueMediumPriority.take();
                } else if (!databaseQueueLowPriority.isEmpty()) {
                    result =  databaseQueueLowPriority.take();
                } else {
                    Thread.sleep(1000);
                    continue;
                }
                if (result != null) {

                    databaseExecutorService.execute(() -> {
                        Result<?> notSavedResult;
                        notSavedResult = databaseManager.saveEntity(result);
                        if (notSavedResult != null) {
                            try {
                                if (result.priority().equals(Priority.HIGH)) {
                                    databaseQueueHighPriority.put(notSavedResult);
                                }
                                if (result.priority().equals(Priority.MEDIUM)) {
                                    databaseQueueMediumPriority.put(notSavedResult);
                                }
                                if (result.priority().equals(Priority.LOW)) {
                                    databaseQueueLowPriority.put(notSavedResult);
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    });
                }
            } catch (InterruptedException e) {
                metricsCollector.incrementTotalDatabaseFailed();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void runFileThread() {
        while (isRunning() || !fileQueue.isEmpty()) {
            try {
                Result<?> takenResult = fileQueue.take();
                fileExecutorService.submit(() -> {
                    fileManager.downloadFile(takenResult);
                });
            } catch (InterruptedException e) {
                metricsCollector.incrementTotalFileFailed();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean hasImageToDownload(Result<?> result) {
        return result.data() instanceof TMDBImageResponse;
    }

    @Override
    public void shutDown() {

    }
}
