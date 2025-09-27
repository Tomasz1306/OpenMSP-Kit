package com.msp.openmsp_kit.service.task;

import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;

public interface TaskExecutor {
    Result<?> process(Task task);
}
