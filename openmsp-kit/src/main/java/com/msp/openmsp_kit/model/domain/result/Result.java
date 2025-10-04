package com.msp.openmsp_kit.model.domain.result;

public record Result<T> (
        String taskId,
        boolean success,
        T data,
        String error
) {}