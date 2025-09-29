package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBCompanyDetailsResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBProductionCompany;
import com.msp.openmsp_kit.model.persistence.entity.TMDBProductionCompanyEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBProductionCompanyMapper implements ToEntityFromApiMapper<TMDBProductionCompanyEntity, TMDBCompanyDetailsResponse>,
    ToEntityFromDomainMapper<TMDBProductionCompanyEntity, TMDBProductionCompany>{

    @Override
    public TMDBProductionCompanyEntity toEntityFromApi(TMDBCompanyDetailsResponse response) {
        return TMDBProductionCompanyEntity
                .builder()
                .id(Long.valueOf(response.id()))
                .name(response.name())
                .description(response.description())
                .headquarters(response.headquarters())
                .homepage(response.homepage())
                .parentCompany(response.parentCompany())
                .logoPath(response.logoPath())
                .originCountry(response.originCountry())
                .build();
    }

    @Override
    public TMDBProductionCompanyEntity toEntityFromDomain(TMDBProductionCompany domain) {
        return TMDBProductionCompanyEntity
                .builder()
                .id(Long.valueOf(domain.getId()))
                .name(domain.getName())
                .logoPath(domain.getLogoPath())
                .originCountry(domain.getOriginCountry())
                .build();
    }
}
