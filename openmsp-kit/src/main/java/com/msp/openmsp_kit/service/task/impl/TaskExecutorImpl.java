package com.msp.openmsp_kit.service.task.impl;

import com.msp.openmsp_kit.model.common.EndPoint;
import com.msp.openmsp_kit.model.common.Resource;
import com.msp.openmsp_kit.model.common.Source;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.downloader.DownloaderRegistry;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskExecutorImpl implements TaskExecutor {

    private final DownloaderRegistry downloaderRegistry;

    public TaskExecutorImpl(DownloaderRegistry downloaderRegistry) {
        this.downloaderRegistry = downloaderRegistry;
    }

    @Override
    public List<Result<?>> process(Task task) {
        try {
            List<Result<?>> results  = new ArrayList<>();
            for (EndPoint endpoint : task.endpoints()) {
                Object data = downloaderRegistry.getDownloader(task.source(), task.resource(), endpoint).fetch(task.id());
                results.add(new Result<>(task.id(), true, data, ""));
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
