package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBLanguageResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBSpokenLanguage;
import com.msp.openmsp_kit.model.persistence.entity.TMDBSpokenLanguageEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBSpokenLanguageMapper implements ToEntityFromApiMapper<TMDBSpokenLanguageEntity, TMDBLanguageResponse>,
    ToEntityFromDomainMapper<TMDBSpokenLanguageEntity, TMDBSpokenLanguage>, ToDomainFromApiMapper<TMDBSpokenLanguage, TMDBLanguageResponse> {

    @Override
    public TMDBSpokenLanguageEntity toEntityFromApi(TMDBLanguageResponse response) {
        return TMDBSpokenLanguageEntity
                .builder()
                .iso6391(response.iso_639_1())
                .name(response.name())
                .englishName(response.englishName())
                .build();
    }

    @Override
    public TMDBSpokenLanguageEntity toEntityFromDomain(TMDBSpokenLanguage domain) {
        return TMDBSpokenLanguageEntity
                .builder()
                .iso6391(domain.getIso_639_1())
                .name(domain.getName())
                .englishName(domain.getEnglishName())
                .build();
    }

    @Override
    public TMDBSpokenLanguage toDomainFromApi(TMDBLanguageResponse response) {
        return TMDBSpokenLanguage
                .builder()
                .iso_639_1(response.iso_639_1())
                .name(response.name())
                .englishName(response.englishName())
                .build();
    }
}
