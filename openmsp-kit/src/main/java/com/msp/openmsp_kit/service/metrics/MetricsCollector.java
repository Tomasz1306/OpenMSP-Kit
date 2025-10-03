package com.msp.openmsp_kit.service.metrics;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MetricsCollector {

    private final AtomicInteger processedTasks = new AtomicInteger(0);
    private final AtomicInteger failedTasks = new AtomicInteger(0);

    private final AtomicInteger totalBatchesProcessed = new AtomicInteger(0);

    private final AtomicInteger totalDatabaseSaves  = new AtomicInteger(0);
    private final AtomicInteger totalFileSaves = new AtomicInteger(0);
    private final AtomicInteger totalDatabaseFailed = new AtomicInteger(0);
    private final AtomicInteger totalFileFailed = new AtomicInteger(0);

    public void incrementProcessedTasks() {
        this.processedTasks.incrementAndGet();
    }
    public void incrementFailedTasks() {
        this.failedTasks.incrementAndGet();
    }
    public void incrementTotalBatchesProcessed() {
        this.totalBatchesProcessed.incrementAndGet();
    }
    public void incrementTotalDatabaseSaves() {
        this.totalDatabaseSaves.incrementAndGet();
    }
    public void incrementTotalFileSaves() {
        this.totalFileSaves.incrementAndGet();
    }
    public void incrementTotalDatabaseFailed() {
        this.totalDatabaseFailed.incrementAndGet();
    }
    public void incrementTotalFileFailed() {
        this.totalFileFailed.incrementAndGet();
    }

    public void printMetrics() {
        System.out.println("Total batches processed: " + this.totalBatchesProcessed.get());
        System.out.println("Total processed tasks: " + this.processedTasks.get());
        System.out.println("Total failed tasks: " + this.failedTasks.get());
        System.out.println("Total database saves: " + this.totalDatabaseSaves.get());
        System.out.println("Total file saves: " + this.totalFileSaves.get());
        System.out.println("Total database failed: " + this.totalDatabaseFailed.get());
        System.out.println("Total file failed: " + this.totalFileFailed.get());
    }
}
