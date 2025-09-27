package com.msp.openmsp_kit.service.scheduler;

import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.batcher.Batcher;
import com.msp.openmsp_kit.service.task.impl.TaskManagerImpl;
import com.msp.openmsp_kit.service.threadManager.impl.ThreadManagerImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Scheduler {

    private final TaskManagerImpl taskManager;
    private final ThreadManagerImpl threadManager;
    private final Batcher batcher;

    public Scheduler(TaskManagerImpl taskManager,
                     ThreadManagerImpl threadManager,
                     Batcher batcher) {
        this.taskManager = taskManager;
        this.threadManager = threadManager;
        this.batcher = batcher;
    }

    public void run() {
        List<Task> tasks = taskManager.createTasks();

        List<List<Task>> batches = batcher.createBatches(tasks);
        List<Result<?>> results = new ArrayList<>();
        for (List<Task> batch : batches) {
             results  = threadManager.processTasks(batch);
        }

        for (var result : results) {
            System.out.println(result);
        }

    }
}
