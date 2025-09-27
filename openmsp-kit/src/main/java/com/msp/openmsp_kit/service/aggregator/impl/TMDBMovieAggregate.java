package com.msp.openmsp_kit.service.aggregator.impl;

import com.msp.openmsp_kit.model.EndPoint;
import com.msp.openmsp_kit.model.dto.MovieDetailsDto;
import com.msp.openmsp_kit.model.movie.impl.TMDBMovie;
import com.msp.openmsp_kit.model.result.Result;

import java.util.Map;

public class TMDBMovieAggregate {
    private final MovieDetailsDto movieDetailsDto;

    public TMDBMovieAggregate(MovieDetailsDto movieDetailsDto) {
        this.movieDetailsDto = movieDetailsDto;
    }

    public TMDBMovieAggregate(Map<EndPoint, Object> endpoints) {
        this.movieDetailsDto = ((Result<MovieDetailsDto>) endpoints.get(EndPoint.DETAILS)).data();
    }

    public TMDBMovie toMovie() {
        return (TMDBMovie) new TMDBMovie.TMDBMovieBuilder()
                .homePage(movieDetailsDto.homepage())
                .status(movieDetailsDto.status())
                .tagline(movieDetailsDto.tagline())
                .revenue(movieDetailsDto.revenue())
                .runtime(movieDetailsDto.runtime())
                .tmdbId(movieDetailsDto.tmdbId())
                .imdbId(movieDetailsDto.imdbId())
                .title(movieDetailsDto.title())
                .originalTitle(movieDetailsDto.originalTitle())
                .releaseDate(movieDetailsDto.releaseDate())
                .overview(movieDetailsDto.overview())
                .language(movieDetailsDto.originalLanguage())
                .adult(movieDetailsDto.adult())
                .budget(movieDetailsDto.budget())
                .boxOffice(null)
                .voteCount(movieDetailsDto.voteCount())
                .voteAverage(movieDetailsDto.voteAverage())
                .popularity(movieDetailsDto.popularity())
                .build();
    }
}
