package com.msp.openmsp_kit.service.task.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.domain.task.Task;
import com.msp.openmsp_kit.service.downloader.DownloaderRegistry;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
                if (data instanceof List<?>) {
                    for (Object item : (List<?>) data) {
                        if (item instanceof TMDBImageResponse) {
                            results.add(new Result<>(((TMDBImageResponse) item).getFilePath(), true, item, ""));
                            continue;
                        }
                        results.add(new Result<>(task.id(), true, item, ""));
                    }
                } else {
                    results.add(new Result<>(task.id(), true, data, ""));
                }
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
