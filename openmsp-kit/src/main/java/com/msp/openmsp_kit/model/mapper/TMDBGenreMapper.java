package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBGenreResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBGenre;
import com.msp.openmsp_kit.model.persistence.entity.TMDBGenreEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBGenreMapper implements ToEntityFromDomainMapper<TMDBGenreEntity, TMDBGenre>,
        ToEntityFromApiMapper<TMDBGenreEntity, TMDBGenreResponse>, ToDomainFromApiMapper<TMDBGenre, TMDBGenreResponse> {

    @Override
    public TMDBGenreEntity toEntityFromApi(TMDBGenreResponse response) {
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
    public TMDBGenre toDomainFromApi(TMDBGenreResponse response) {
        return TMDBGenre
                .builder()
                .tmdbId(response.tmdbId())
                .name(response.name())
                .build();
    }
}
