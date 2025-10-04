package com.msp.openmsp_kit.service.metrics;

import org.springframework.scheduling.annotation.Scheduled;
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

    private final AtomicInteger databaseQueueSize = new AtomicInteger(0);
    private final AtomicInteger fileQueueSize = new AtomicInteger(0);

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

    public void setdataBaseQueueSize(int size) {
        this.databaseQueueSize.set(size);
    }
    public void setfileQueueSize(int size) {
        this.fileQueueSize.set(size);
    }

    @Scheduled(fixedRate = 5000)
    public void printMetrics() {
        System.out.printf(
                "Batches: %d | Tasks: %d | Failed: %d | DB Saves: %d | File Saves: %d | DB Failed: %d | File Failed: %d | DB queue size: %d | FILE queue size: %d%n",
                totalBatchesProcessed.get(),
                processedTasks.get(),
                failedTasks.get(),
                totalDatabaseSaves.get(),
                totalFileSaves.get(),
                totalDatabaseFailed.get(),
                totalFileFailed.get(),
                databaseQueueSize.get(),
                fileQueueSize.get()
        );
    }

}
