package com.msp.openmsp_kit.service.threadManager.impl;

import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.rateLimiter.RateLimiter;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ThreadManagerImpl {
    private final TaskExecutor taskExecutor;
    private final RateLimiter rateLimiter;

    public ThreadManagerImpl(TaskExecutor taskExecutor,
                             RateLimiter rateLimiter) {
        this.taskExecutor = taskExecutor;
        this.rateLimiter = rateLimiter;
    }

    public List<Result<?>> processTasks(List<Task> tasks) {
        List<List<Result<?>>> results = tasks
                .stream()
                .map(
                    task -> {
                        return CompletableFuture.supplyAsync(() -> {
                            rateLimiter.acquire();
                            return taskExecutor.process(task);
                        });
                    }
                ).map(
                        future -> {
                            try {
                                return future.get();
                            } catch (InterruptedException | ExecutionException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ).collect(Collectors.toList());

        List<Result<?>> resultList = results
                .stream()
                .flatMap(Collection::stream)
                .toList();


        for (Result<?> result : resultList) {
            System.out.println(result.toString());
        }
        return resultList;
    }

}
