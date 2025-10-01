package com.msp.openmsp_kit.service.threadManager.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.database.DatabaseManager;
import com.msp.openmsp_kit.service.file.FileManager;
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

    private enum Status {
        RUNNING, PAUSED, STOPPED, ERROR
    }

    private final BlockingQueue<Result<?>> databaseQueue;
    private final BlockingQueue<Result<?>> fileQueue;

    private final ExecutorService downloaderService;
    private final ExecutorService databaseService;
    private final ExecutorService fileService;
    private final Object lock = new Object();
    private Status status = Status.STOPPED;


    ThreadManagerImpl(TaskExecutor taskExecutor, RateLimiter rateLimiter, DatabaseManager databaseManager, FileManager fileManager) {
        status = Status.RUNNING;

        this.taskExecutor = taskExecutor;
        this.rateLimiter = rateLimiter;

        downloaderService = Executors.newFixedThreadPool(3);
        databaseService = Executors.newFixedThreadPool(2);
        fileService = Executors.newFixedThreadPool(3);

        databaseQueue = new LinkedBlockingQueue<>(100);
        fileQueue = new LinkedBlockingQueue<>(1000);
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
        List<Result<?>> results = batch
                .stream()
                .map(task -> {
                    return CompletableFuture.supplyAsync(() -> {
                        rateLimiter.acquire();
                        return taskExecutor.process(task);
                    });
                })
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .toList();
        for (Result<?> result : results) {
            try {
                databaseQueue.put(result);
                if (hasImageToSave(result)) {
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void runFileThread() {
        while (isRunning() || !fileQueue.isEmpty()) {
            try {
                fileManager.downloadFile(fileQueue.take());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean hasImageToSave(Result<?> result) {
        return result.data() instanceof TMDBImageResponse;
    }

    @Override
    public void shutDown() {

    }
}
