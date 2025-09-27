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
                .homePage(movieDetailsDto.homepage() != null ? movieDetailsDto.homepage() :  null)
                .status(movieDetailsDto.status() != null ? movieDetailsDto.status() : null)
                .tagline(movieDetailsDto.tagline() != null ? movieDetailsDto.tagline() : null)
                .revenue(movieDetailsDto.revenue())
                .runtime(movieDetailsDto.runtime())
                .genre(movieDetailsDto.genres() != null ? movieDetailsDto.genres() : null)
                .productionCompanies(movieDetailsDto.productionCOmpanies() != null ? movieDetailsDto.productionCOmpanies() : null)
                .productionCountry(movieDetailsDto.productionCountries() != null ? movieDetailsDto.productionCountries() : null)
                .spokenLanguage(movieDetailsDto.spokenLanguages() != null ? movieDetailsDto.spokenLanguages() : null)
                .tmdbId(movieDetailsDto.tmdbId())
                .imdbId(movieDetailsDto.imdbId() != null ? movieDetailsDto.imdbId() : null)
                .title(movieDetailsDto.title() != null ? movieDetailsDto.title() : null)
                .originalTitle(movieDetailsDto.originalTitle() != null ? movieDetailsDto.originalTitle() : null)
                .releaseDate(movieDetailsDto.releaseDate()  != null ? movieDetailsDto.releaseDate() : null)
                .overview(movieDetailsDto.overview()  != null ? movieDetailsDto.overview() : null)
                .language(movieDetailsDto.originalLanguage()  != null ? movieDetailsDto.originalLanguage() : null)
                .adult(movieDetailsDto.adult())
                .budget(movieDetailsDto.budget())
                .boxOffice(null)
                .voteCount(movieDetailsDto.voteCount())
                .voteAverage(movieDetailsDto.voteAverage())
                .popularity(movieDetailsDto.popularity())
                .build();
    }
}
