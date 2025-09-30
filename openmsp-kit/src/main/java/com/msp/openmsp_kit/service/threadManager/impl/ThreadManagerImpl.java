package com.msp.openmsp_kit.service.threadManager.impl;

import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.database.DatabaseManager;
import com.msp.openmsp_kit.service.rateLimiter.RateLimiter;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class ThreadManagerImpl {
    private final TaskExecutor taskExecutor;
    private final RateLimiter rateLimiter;

    private final DatabaseManager databaseManager;
    private final BlockingQueue<List<Result<?>>> saveQueue;
    private final ExecutorService saveExecutor;


    public ThreadManagerImpl(TaskExecutor taskExecutor,
                             RateLimiter rateLimiter,
                             DatabaseManager databaseManager) {
        this.taskExecutor = taskExecutor;
        this.rateLimiter = rateLimiter;
        this.databaseManager = databaseManager;

        this.saveQueue = new ArrayBlockingQueue<>(5);
        this.saveExecutor = Executors.newFixedThreadPool(3);


    }

    public void runTasks(List<List<Task>> batches) {
        CompletableFuture<Void> saveEntity = startSaveConsumer();
        try {
            for  (List<Task> batch : batches) {
                List<Result<?>> batchResult = batch
                        .stream()
                        .map(task -> {
                            return CompletableFuture.supplyAsync(() -> {
                                rateLimiter.acquire();
                                return taskExecutor.process(task);
                            });
                        }).map(future -> {
                            try {
                                return future.get();
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .flatMap(Collection::stream)
                        .toList();

                if (!batchResult.isEmpty()) {
                    saveQueue.offer(batchResult);
                }

            }
            saveEntity.join();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private CompletableFuture<Void> startSaveConsumer() {
        return CompletableFuture.runAsync(() -> {
            while(!Thread.currentThread().isInterrupted()) {
                try {
                    if (!databaseManager.isSaving()) {
                        databaseManager.saveEntity(saveQueue.take());
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void shutdown() {
        saveExecutor.shutdown();
    }
}
