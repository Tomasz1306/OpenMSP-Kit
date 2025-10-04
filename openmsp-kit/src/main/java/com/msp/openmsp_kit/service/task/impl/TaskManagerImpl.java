package com.msp.openmsp_kit.service.task.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieId;
import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.common.Resource;
import com.msp.openmsp_kit.model.domain.common.Source;
import com.msp.openmsp_kit.model.domain.task.Task;
import com.msp.openmsp_kit.service.dataLoader.impl.TMDBMovieDataLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskManagerImpl {

    private final TMDBMovieDataLoader tmdbMovieDataLoader;

    public TaskManagerImpl(TMDBMovieDataLoader tMDBMovieDataLoader) {
        this.tmdbMovieDataLoader = tMDBMovieDataLoader;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.addAll(getTMDBNecessaryData());
        tasks.addAll(createTMDBTasks());
        return tasks;
    }

    private List<Task> createTMDBTasks() {
        List<TMDBMovieId> movieIds = tmdbMovieDataLoader.loadData();
        return new ArrayList<>(movieIds.stream()
                .map(movie -> new Task(
                        Integer.toString(movie.id()),
                        Source.TMDB,
                        Resource.MOVIE,
                        Set.of(EndPoint.DETAILS, EndPoint.IMAGES)
                )).toList());
    }

    public List<Task> getTMDBNecessaryData() {
        return List.of(
                new Task("", Source.TMDB, Resource.GENRES, Set.of(EndPoint.CONFIG)),
                new Task("", Source.TMDB, Resource.COUNTRIES, Set.of(EndPoint.CONFIG)),
//                new Task("", Source.TMDB, Resource.COMPANIES, Set.of(EndPoint.DETAILS)),
                new Task("", Source.TMDB, Resource.LANGUAGES, Set.of(EndPoint.CONFIG))
        );
    }
}
