package com.msp.openmsp_kit.service.threadManager;

import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.domain.task.Task;

import java.util.List;

public interface ThreadManager {
    boolean isRunning();
    boolean isPaused();
    void pause();
    void start();
    void resume();
    void stop();


    void runThreads();
    void stopThreads();
    void processBatch(List<Task> batch);
    void processBatches(List<List<Task>> batches);
    void runDatabaseThread();
    void runFileThread();

    boolean hasImageToDownload(Result<?> result);

    void shutDown();
}
