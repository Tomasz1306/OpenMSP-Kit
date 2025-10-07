package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBWatchProviderResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBWatchProvider;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBWatchProviderEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBWatchProviderMapper implements ToEntityFromApiMapper<TMDBWatchProviderEntity, TMDBWatchProviderResponse>,
        ToEntityFromDomainMapper<TMDBWatchProviderEntity, TMDBWatchProvider>, ToDomainFromApiMapper<TMDBWatchProvider, TMDBWatchProviderResponse>{
    @Override
    public TMDBWatchProvider toDomainFromApi(TMDBWatchProviderResponse response) {
        return TMDBWatchProvider
                .builder()
                .providerId((long) response.providerId())
                .displayPriority((long) response.displayPriority())
                .providerName(response.providerName())
                .logoPath(response.logoPath())
                .build();
    }

    @Override
    public TMDBWatchProviderEntity toEntityFromApi(TMDBWatchProviderResponse response) {
        return TMDBWatchProviderEntity
                .builder()
                .providerId((long) response.providerId())
                .displayPriority((long) response.displayPriority())
                .providerName(response.providerName())
                .logoPath(response.logoPath())
                .build();
    }

    @Override
    public TMDBWatchProviderEntity toEntityFromDomain(TMDBWatchProvider domain) {
        return TMDBWatchProviderEntity
                .builder()
                .providerId(domain.getProviderId())
                .displayPriority(domain.getDisplayPriority())
                .providerName(domain.getProviderName())
                .logoPath(domain.getLogoPath())
                .build();
    }
}
