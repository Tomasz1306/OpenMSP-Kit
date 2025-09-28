package com.msp.openmsp_kit.service.task.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieDetailsResponse;
import com.msp.openmsp_kit.model.common.EndPoint;
import com.msp.openmsp_kit.model.common.Resource;
import com.msp.openmsp_kit.model.common.Source;
import com.msp.openmsp_kit.model.domain.movie.TMDBMovieImpl;
import com.msp.openmsp_kit.model.mapper.TMDBMovieMapper;
import com.msp.openmsp_kit.model.task.Task;
import com.msp.openmsp_kit.service.dataLoader.impl.TMDBMovieDataLoader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import com.msp.openmsp_kit.service.task.TaskManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskManagerImpl implements TaskManager {

    private final TMDBMovieDataLoader tmdbMovieDataLoader;
    private final JsonParser jsonParser;

    public TaskManagerImpl(TMDBMovieDataLoader tMDBMovieDataLoader,
                           JsonParser jsonParser) {
        this.tmdbMovieDataLoader = tMDBMovieDataLoader;
        this.jsonParser = jsonParser;
    }

    @Override
    public List<Task> createTasks() {

        List<TMDBMovieImpl> tmdbMovies = loadTMDBMovies();
        System.out.println("TMDB Movies: " + tmdbMovies);

        List<Task> tasks = new ArrayList<>(tmdbMovies.stream()
                .map(movie -> new Task(
                        Integer.toString(movie.getTmdbId()),
                        Source.TMDB,
                        Resource.MOVIE,
                        Set.of(EndPoint.DETAILS)))
                .toList());

        return tasks;
    }

    private List<TMDBMovieImpl> loadTMDBMovies() {
        return tmdbMovieDataLoader
                .loadData()
                .stream()
                .map(jsonData -> {
                    try {
                        return jsonParser.parseJsonToObject(jsonData, TMDBMovieDetailsResponse.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).map(TMDBMovieMapper::toDomain).toList();
    }
}
