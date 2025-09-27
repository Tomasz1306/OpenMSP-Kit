package com.msp.openmsp_kit.service.task.impl;

import com.msp.openmsp_kit.model.EndPoint;
import com.msp.openmsp_kit.model.Resource;
import com.msp.openmsp_kit.model.Source;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.aggregator.impl.TMDBMovieAggregate;
import com.msp.openmsp_kit.service.downloader.DownloaderRegistry;
import com.msp.openmsp_kit.service.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskExecutorImpl implements TaskExecutor {

    private final DownloaderRegistry downloaderRegistry;

    public TaskExecutorImpl(DownloaderRegistry downloaderRegistry) {
        this.downloaderRegistry = downloaderRegistry;
    }

    @Override
    public Result<?> process(Task task) {
        try {
            Map<EndPoint, Object> endpoints = fetchAllEndpoints(task);
            Object data = assembleData(task, endpoints);
            return new Result<>(task.id(), true, data, "");
        } catch (Exception e) {
            throw new  RuntimeException(e);
        }
    }


    private Map<EndPoint, Object> fetchAllEndpoints(Task task) {
        Map<EndPoint, Object> results  = new HashMap<>();
        for (EndPoint endpoint : task.endpoints()) {
            Object data = downloaderRegistry.getDownloader(task.source(), task.resource(), endpoint).fetch(task.id());

            results.put(endpoint, data);
        }
        return results;
    }

    private Object assembleData(Task task, Map<EndPoint, Object> results) {
        if (task.source().equals(Source.TMDB)) {
            if (task.resource().equals(Resource.MOVIE)) {
                TMDBMovieAggregate aggregator = new TMDBMovieAggregate(results);
                return aggregator.toMovie();
            }
        }
        return null;
    }
}
