package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieGenreResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBGenre;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBGenreEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBGenreMapper implements ToEntityFromDomainMapper<TMDBGenreEntity, TMDBGenre>,
        ToEntityFromApiMapper<TMDBGenreEntity, TMDBMovieGenreResponse>, ToDomainFromApiMapper<TMDBGenre, TMDBMovieGenreResponse> {

    @Override
    public TMDBGenreEntity toEntityFromApi(TMDBMovieGenreResponse response) {
        return TMDBGenreEntity
                .builder()
                .tmdbId(Long.valueOf(response.tmdbId()))
                .name(response.name())
                .build();
    }

    @Override
    public TMDBGenreEntity toEntityFromDomain(TMDBGenre domain) {
        return TMDBGenreEntity
                .builder()
                .tmdbId(Long.valueOf(domain.getId()))
                .iso_639_1(domain.getIso_639_1())
                .name(domain.getName())
                .build();
    }

    @Override
    public TMDBGenre toDomainFromApi(TMDBMovieGenreResponse response) {
        return TMDBGenre
                .builder()
                .tmdbId(response.tmdbId())
                .name(response.name())
                .build();
    }
}
