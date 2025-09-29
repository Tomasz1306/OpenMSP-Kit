package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBCompanyDetailsResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBCountryResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBProductionCompany;
import com.msp.openmsp_kit.model.domain.movie.TMDBProductionCountry;
import com.msp.openmsp_kit.model.persistence.entity.TMDBProductionCompanyEntity;
import com.msp.openmsp_kit.model.persistence.entity.TMDBProductionCountryEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBProductionCountryMapper implements ToEntityFromApiMapper<TMDBProductionCountryEntity, TMDBCountryResponse>,
    ToEntityFromDomainMapper<TMDBProductionCountryEntity, TMDBProductionCountry> {

    @Override
    public TMDBProductionCountryEntity toEntityFromApi(TMDBCountryResponse response) {
        return TMDBProductionCountryEntity
                .builder()
                .iso_3166_1(response.iso_3166_1())
                .englishName(response.englishName())
                .nativeName(response.nativeName())
                .build();
    }

    @Override
    public TMDBProductionCountryEntity toEntityFromDomain(TMDBProductionCountry domain) {
        return TMDBProductionCountryEntity
                .builder()
                .iso_3166_1(domain.getIso_3166_1())
                .englishName(domain.getName())
                .build();
    }
}
