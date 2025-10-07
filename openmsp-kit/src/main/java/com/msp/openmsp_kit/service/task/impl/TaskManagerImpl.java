package com.msp.openmsp_kit.service.task.impl;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieId;
import com.msp.openmsp_kit.model.api.tmdb.TMDBPersonId;
import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.common.Resource;
import com.msp.openmsp_kit.model.domain.common.Source;
import com.msp.openmsp_kit.model.domain.task.Task;
import com.msp.openmsp_kit.service.dataLoader.impl.TMDBMovieDataLoader;
import com.msp.openmsp_kit.service.dataLoader.impl.TMDBPersonDataLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskManagerImpl {

    private final TMDBMovieDataLoader tmdbMovieDataLoader;
    private final TMDBPersonDataLoader tmdbPersonDataLoader;

    public TaskManagerImpl(TMDBMovieDataLoader tMDBMovieDataLoader,
                           TMDBPersonDataLoader tmdbPersonDataLoader) {
        this.tmdbMovieDataLoader = tMDBMovieDataLoader;
        this.tmdbPersonDataLoader = tmdbPersonDataLoader;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();

        tasks.addAll(getTMDBNecessaryData());
        tasks.addAll(createTMDBTasks());
        return tasks;
    }

    private List<Task> createTMDBTasks() {
        List<TMDBMovieId> movieIds = tmdbMovieDataLoader.loadData();
        List<TMDBPersonId> personIds = tmdbPersonDataLoader.loadData();
        List<Task>  tasks = new ArrayList<>();
        tasks.addAll(movieIds.stream()
                .map(movie -> new Task(
                        Integer.toString(movie.id()),
                        Source.TMDB,
                        Resource.MOVIE,
                        Set.of(EndPoint.DETAILS, EndPoint.IMAGES, EndPoint.WATCH_PROVIDERS, EndPoint.VIDEOS)
                )).toList());
        tasks.addAll(personIds.stream()
                .map(person -> new Task(
                        Long.toString(person.id()),
                        Source.TMDB,
                        Resource.PEOPLE,
                        Set.of(EndPoint.DETAILS)
                )).toList());
        return tasks;
    }

    public List<Task> getTMDBNecessaryData() {
        return List.of(
                new Task("", Source.TMDB, Resource.GENRES, Set.of(EndPoint.CONFIG)),
                new Task("", Source.TMDB, Resource.COUNTRIES, Set.of(EndPoint.CONFIG)),
//                new Task("", Source.TMDB, Resource.COMPANIES, Set.of(EndPoint.DETAILS)),
                new Task("", Source.TMDB, Resource.LANGUAGES, Set.of(EndPoint.CONFIG)),
                new Task("", Source.TMDB, Resource.WATCH_PROVIDERS, Set.of(EndPoint.CONFIG))
        );
    }
}
