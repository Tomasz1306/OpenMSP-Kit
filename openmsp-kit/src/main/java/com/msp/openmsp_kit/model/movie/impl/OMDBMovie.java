package com.msp.openmsp_kit.model.movie.impl;

import com.msp.openmsp_kit.model.movie.AbstractMovie;
import com.msp.openmsp_kit.model.movie.MovieAwardsInfo;
import com.msp.openmsp_kit.model.movie.MoviePeopleInfo;

import java.util.List;
import java.util.Optional;

public class OMDBMovie extends AbstractMovie
    implements MovieAwardsInfo, MoviePeopleInfo {

    private String awards;
    private String director;
    private String writer;
    private String actors;

    public OMDBMovie(OMDBMovieBuilder omdbMovieBuilder) {
        super(omdbMovieBuilder);
        this.awards = omdbMovieBuilder.awards;
        this.director = omdbMovieBuilder.director;
        this.writer = omdbMovieBuilder.writer;
        this.actors = omdbMovieBuilder.actors;
    }

    @Override
    public List<Genre> getGenres() {
        return List.of();
    }


    public static class OMDBMovieBuilder extends AbstractMovieBuilder {
        private String awards;
        private String director;
        private String writer;
        private String actors;

        public OMDBMovieBuilder awards(String awards) { this.awards = awards; return this; }
        public OMDBMovieBuilder director(String director) { this.director = director; return this; }
        public OMDBMovieBuilder writer(String writer) { this.writer = writer; return this; }
        public OMDBMovieBuilder actors(String actors) { this.actors = actors; return this; }

        @Override
        public OMDBMovie build() {
            return new OMDBMovie(this);
        }
    }

    @Override
    public Optional<String> getAwards() {
        return awards != null ? Optional.of(awards) : Optional.empty();
    }

    @Override
    public Optional<String> getDirector() {
        return director != null ? Optional.of(director) : Optional.empty();
    }

    @Override
    public Optional<String> getWriter() {
        return writer != null ? Optional.of(writer) : Optional.empty();
    }

    @Override
    public Optional<String> getActors() {
        return actors != null ? Optional.of(actors) : Optional.empty();
    }
}
