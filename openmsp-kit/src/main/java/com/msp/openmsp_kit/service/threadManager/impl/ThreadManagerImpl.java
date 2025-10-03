package com.msp.openmsp_kit.service.threadManager.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.database.DatabaseManager;
import com.msp.openmsp_kit.service.file.FileManager;
import com.msp.openmsp_kit.service.metrics.MetricsCollector;
import com.msp.openmsp_kit.service.rateLimiter.RateLimiter;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import com.msp.openmsp_kit.service.threadManager.ThreadManager;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

@Service
public class ThreadManagerImpl implements ThreadManager {

    private final TaskExecutor taskExecutor;
    private final RateLimiter rateLimiter;
    private final DatabaseManager databaseManager;
    private final FileManager fileManager;
    private final MetricsCollector metricsCollector;

    private enum Status {
        RUNNING, PAUSED, STOPPED, ERROR
    }

    private final BlockingQueue<Result<?>> databaseQueue;
    private final BlockingQueue<Result<?>> fileQueue;

    private final ExecutorService downloaderService;
    private final ExecutorService databaseService;
    private final ExecutorService fileService;
    private final Object lock = new Object();
    private Status status;
    private static final int numberOfThreads = Runtime.getRuntime().availableProcessors();


    ThreadManagerImpl(TaskExecutor taskExecutor,
                      RateLimiter rateLimiter,
                      DatabaseManager databaseManager,
                      FileManager fileManager,
                      MetricsCollector metricsCollector) {
        status = Status.RUNNING;

        this.taskExecutor = taskExecutor;
        this.rateLimiter = rateLimiter;
        this.metricsCollector = metricsCollector;

        downloaderService = Executors.newFixedThreadPool((int) (numberOfThreads * 0.5));
        databaseService = Executors.newFixedThreadPool((int) (numberOfThreads * 0.2));
        fileService = Executors.newFixedThreadPool((int) (numberOfThreads * 0.3));

        databaseQueue = new LinkedBlockingQueue<>(200);
        fileQueue = new LinkedBlockingQueue<>(500);
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
        CompletableFuture.runAsync(this::runDatabaseThread, databaseService);
        CompletableFuture.runAsync(this::runFileThread, fileService);

    }

    @Override
    public void stopThreads() {
        databaseService.shutdown();
        downloaderService.shutdown();
        fileService.shutdown();
    }

    @Override
    public void processBatches(List<List<Task>> batches) {
        if (!isRunning()) {
            System.out.println("ThreadManager is not running");
            return;
        }
        for (List<Task> batch : batches) {
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
                databaseQueue.put(result);
                if (hasImageToDownload(result)) {
                    fileQueue.put(result);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void runDatabaseThread() {
        while (isRunning() || !databaseQueue.isEmpty()) {
            try {
                databaseManager.saveEntity(databaseQueue.take());
                metricsCollector.incrementTotalDatabaseSaves();
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
                fileManager.downloadFile(fileQueue.take());
                metricsCollector.incrementTotalFileSaves();
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
