package com.msp.openmsp_kit.service.batcher.impl;

import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.batcher.Batcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatcherImpl implements Batcher {

    private final int BATCH_SIZE = 5;

    @Override
    public List<List<Task>> createBatches(List<Task> tasks) {
        int numberOfLists = tasks.size() / BATCH_SIZE + 1;
        List<List<Task>> batches = new ArrayList<>();
        for (int i = 0; i < numberOfLists; i++) {
            batches.add(tasks.subList(i * BATCH_SIZE, Math.min((i + 1) * BATCH_SIZE,  tasks.size())));
        }
        return batches;
    }
}
