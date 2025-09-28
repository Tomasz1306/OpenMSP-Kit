package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieDetailsResponse;
import com.msp.openmsp_kit.model.domain.movie.Movie;
import com.msp.openmsp_kit.model.domain.movie.TMDBMovieImpl;
import org.springframework.stereotype.Component;

@Component
public class TMDBMovieMapper {
    public static TMDBMovieImpl toDomain(TMDBMovieDetailsResponse tmdbMovieDetailsResponse) {
        return TMDBMovieImpl
                .builder()
                .tmdbId(tmdbMovieDetailsResponse.tmdbId())
                .imdbId(tmdbMovieDetailsResponse.imdbId())
                .title(tmdbMovieDetailsResponse.title())
                .overview(tmdbMovieDetailsResponse.overview())
                .releaseDate(tmdbMovieDetailsResponse.releaseDate())
                .backdropPath(tmdbMovieDetailsResponse.backdropPath())
                .posterPath(tmdbMovieDetailsResponse.posterPath())
                .homepage(tmdbMovieDetailsResponse.homepage())
                .budget(tmdbMovieDetailsResponse.budget())
                .language(tmdbMovieDetailsResponse.originalLanguage())
                .popularity(tmdbMovieDetailsResponse.popularity())
                .voteAverage(tmdbMovieDetailsResponse.voteAverage())
                .voteCount(tmdbMovieDetailsResponse.voteCount())
                .genres(tmdbMovieDetailsResponse.genres())
                .productionCountries(tmdbMovieDetailsResponse.productionCountries())
                .productionCompanies(tmdbMovieDetailsResponse.productionCompanies())
                .spokenLanguages(tmdbMovieDetailsResponse.spokenLanguages())
                .adult(tmdbMovieDetailsResponse.adult())
                .video(tmdbMovieDetailsResponse.video())
                .build();
    }
}
