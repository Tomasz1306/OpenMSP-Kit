package com.msp.openmsp_kit.service.task.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.msp.openmsp_kit.model.EndPoint;
import com.msp.openmsp_kit.model.Resource;
import com.msp.openmsp_kit.model.Source;
import com.msp.openmsp_kit.model.movie.impl.TMDBMovie;
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

        List<TMDBMovie> tmdbMovies = loadTMDBMovies();

        List<Task> tasks = new ArrayList<>(tmdbMovies.stream()
                .map(movie -> new Task(
                        movie.getTmdbId().toString(),
                        Source.TMDB,
                        Resource.MOVIE,
                        Set.of(EndPoint.DETAILS)))
                .toList());

        return tasks;
    }

    private List<TMDBMovie> loadTMDBMovies() {
        return tmdbMovieDataLoader
                .loadData()
                .stream()
                .map(jsonData -> {
                    try {
                        return jsonParser.parseJsonToObject(jsonData, TMDBMovie.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }
}
