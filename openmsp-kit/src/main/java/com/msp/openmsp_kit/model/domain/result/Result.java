package com.msp.openmsp_kit.model.domain.result;

import com.msp.openmsp_kit.model.domain.common.Priority;

public record Result<T> (
        String taskId,
        boolean success,
        T data,
        String error,
        Priority priority
) {}