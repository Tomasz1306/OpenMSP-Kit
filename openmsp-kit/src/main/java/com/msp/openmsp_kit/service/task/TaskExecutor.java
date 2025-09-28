package com.msp.openmsp_kit.service.task;

import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;

import java.util.List;

public interface TaskExecutor {
    List<Result<?>> process(Task task);
}
