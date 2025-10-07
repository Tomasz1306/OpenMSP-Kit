package com.msp.openmsp_kit.service.task.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.common.Priority;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.domain.task.Task;
import com.msp.openmsp_kit.model.domain.tmdb.*;
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
                        Priority priority = determinePriority(item);
                        if (item instanceof TMDBMovieImage) {
                            results.addLast(new Result<>(((TMDBMovieImage) item).getFilePath(), true, item, "", priority));
                            continue;
                        } else if (item instanceof TMDBPersonImage) {
                            results.addLast(new Result<>(((TMDBPersonImage) item).getFilePath(), true, item, "", priority));
                            continue;
                        }
                        results.addLast(new Result<>(task.id(), true, item, "", priority));
                    }
                } else {
                    Priority priority = determinePriority(data);
                    results.addLast(new Result<>(task.id(), true, data, "", priority));
                }
            }
            return results;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Priority determinePriority(Object object) {
        if  (object instanceof TMDBGenre ||
             object instanceof TMDBSpokenLanguage ||
             object instanceof TMDBProductionCountry) {
            return Priority.HIGH;
        } else if (object instanceof TMDBMovie ||
                   object instanceof TMDBPerson) {
            return Priority.MEDIUM;
        } else if (object instanceof TMDBImageResponse ||
                   object instanceof TMDBMovieWatchProvider) {
            return Priority.LOW;
        }
        return Priority.LOW;
    }
}
