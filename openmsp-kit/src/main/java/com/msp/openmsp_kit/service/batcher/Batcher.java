package com.msp.openmsp_kit.service.batcher;

import com.msp.openmsp_kit.model.domain.task.Task;

import java.util.List;

public interface Batcher {
    List<List<Task>> createBatches(List<Task> tasks);
}
