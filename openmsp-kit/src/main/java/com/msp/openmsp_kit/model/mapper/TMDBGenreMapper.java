package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBGenreResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBGenre;
import com.msp.openmsp_kit.model.persistence.entity.TMDBGenreEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBGenreMapper implements ToEntityFromDomainMapper<TMDBGenreEntity, TMDBGenre>, ToEntityFromApiMapper<TMDBGenreEntity, TMDBGenreResponse> {

    @Override
    public TMDBGenreEntity toEntityFromApi(TMDBGenreResponse response) {
        return TMDBGenreEntity
                .builder()
                .id(Long.valueOf(response.id()))
                .name(response.name())
                .build();
    }

    @Override
    public TMDBGenreEntity toEntityFromDomain(TMDBGenre domain) {
        return TMDBGenreEntity
                .builder()
                .id(Long.valueOf(domain.getId()))
                .name(domain.getName())
                .build();
    }
}
