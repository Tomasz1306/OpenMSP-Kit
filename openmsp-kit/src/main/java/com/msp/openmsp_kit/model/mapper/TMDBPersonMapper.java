package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBPersonDetailsResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBPerson;
import com.msp.openmsp_kit.model.persistence.entity.person.TMDBPersonEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBPersonMapper implements ToEntityFromDomainMapper<TMDBPersonEntity, TMDBPerson>,
        ToEntityFromApiMapper<TMDBPersonEntity, TMDBPersonDetailsResponse>, ToDomainFromApiMapper<TMDBPerson, TMDBPersonDetailsResponse>{
    @Override
    public TMDBPerson toDomainFromApi(TMDBPersonDetailsResponse response) {
        return TMDBPerson
                .builder()
                .tmdbId(response.tmdbId())
                .name(response.name())
                .imdbId(response.imdbId())
                .adult(response.adult())
                .alsoKnownAs(response.alsoKnownAs())
                .biography(response.biography())
                .birthday(response.birthday())
                .gender(response.gender())
                .deathday(response.deathday())
                .popularity(response.popularity())
                .knownForDepartment(response.knownForDepartment())
                .placeOfBirth(response.placeOfBirth())
                .profilePath(response.profilePath())
                .homepage(response.homepage())
                .build();
    }

    @Override
    public TMDBPersonEntity toEntityFromApi(TMDBPersonDetailsResponse domain) {
        return null;
    }

    @Override
    public TMDBPersonEntity toEntityFromDomain(TMDBPerson domain) {
        return TMDBPersonEntity
                .builder()
                .iso6391(domain.getIso_639_1())
                .tmdbId(domain.getTmdbId())
                .name(domain.getName())
                .imdbId(domain.getImdbId())
                .adult(domain.isAdult())
                .alsoKnownAs(domain.getAlsoKnownAs())
                .biography(domain.getBiography())
                .birthday(domain.getBirthday())
                .gender(domain.getGender())
                .deathday(domain.getDeathday())
                .popularity(domain.getPopularity())
                .knownForDepartment(domain.getKnownForDepartment())
                .placeOfBirth(domain.getPlaceOfBirth())
                .profilePath(domain.getProfilePath())
                .homepage(domain.getHomepage())
                .build();
    }
}
