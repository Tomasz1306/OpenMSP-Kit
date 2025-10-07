package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBCountryResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBProductionCountry;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBProductionCountryEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBProductionCountryMapper implements ToEntityFromApiMapper<TMDBProductionCountryEntity, TMDBCountryResponse>,
    ToEntityFromDomainMapper<TMDBProductionCountryEntity, TMDBProductionCountry>, ToDomainFromApiMapper<TMDBProductionCountry, TMDBCountryResponse> {

    @Override
    public TMDBProductionCountryEntity toEntityFromApi(TMDBCountryResponse response) {
        return TMDBProductionCountryEntity
                .builder()
                .iso31661(response.iso_3166_1())
                .englishName(response.englishName())
                .nativeName(response.nativeName())
                .build();
    }

    @Override
    public TMDBProductionCountryEntity toEntityFromDomain(TMDBProductionCountry domain) {
        return TMDBProductionCountryEntity
                .builder()
                .iso31661(domain.getIso_3166_1())
                .englishName(domain.getEnglishName())
                .nativeName(domain.getNativeName())
                .build();
    }

    @Override
    public TMDBProductionCountry toDomainFromApi(TMDBCountryResponse response) {
        return TMDBProductionCountry
                .builder()
                .iso_3166_1(response.iso_3166_1())
                .englishName(response.englishName())
                .nativeName(response.nativeName())
                .build();
    }
}
