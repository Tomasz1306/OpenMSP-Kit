package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieWatchProviderResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBMovieWatchProvider;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBMovieProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBMovieWatchProviderMapper implements ToEntityFromApiMapper<TMDBMovieProviderEntity, TMDBMovieWatchProviderResponse.Provider>,
        ToEntityFromDomainMapper<TMDBMovieProviderEntity, TMDBMovieWatchProvider>{

    @Override
    public TMDBMovieProviderEntity toEntityFromApi(TMDBMovieWatchProviderResponse.Provider domain) {
        return TMDBMovieProviderEntity
                .builder()
                .build();
    }

    @Override
    public TMDBMovieProviderEntity toEntityFromDomain(TMDBMovieWatchProvider domain) {
        return TMDBMovieProviderEntity
                .builder()
                .type(domain.getType())
                .link(domain.getLink())
                .build();
    }
}
