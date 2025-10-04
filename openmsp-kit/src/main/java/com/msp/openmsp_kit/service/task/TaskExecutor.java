package com.msp.openmsp_kit.service.task;

import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.domain.task.Task;

import java.util.List;

public interface TaskExecutor {
    List<Result<?>> process(Task task);
}
