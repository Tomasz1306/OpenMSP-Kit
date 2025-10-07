package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBPersonImage;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBImageEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBPersonImageMapper implements ToEntityFromDomainMapper<TMDBImageEntity, TMDBPersonImage>,
        ToEntityFromApiMapper<TMDBImageEntity, TMDBImageResponse>, ToDomainFromApiMapper<TMDBPersonImage, TMDBImageResponse>{
    @Override
    public TMDBPersonImage toDomainFromApi(TMDBImageResponse response) {
        return TMDBPersonImage
                .builder()
                .type(response.type())
                .width(response.width())
                .height(response.height())
                .aspectRatio(response.aspectRatio())
                .filePath(response.filePath())
                .iso6391(response.iso_639_1())
                .voteAverage(response.voteAverage())
                .voteCount(response.voteCount())
                .build();
    }

    @Override
    public TMDBImageEntity toEntityFromApi(TMDBImageResponse domain) {
        return null;
    }

    @Override
    public TMDBImageEntity toEntityFromDomain(TMDBPersonImage domain) {
        return TMDBImageEntity
                .builder()
                .type(domain.getType())
                .width(domain.getWidth())
                .height(domain.getHeight())
                .aspectRatio(domain.getAspectRatio())
                .filePath(domain.getFilePath())
                .iso_639_1(domain.getIso6391())
                .voteAverage(domain.getVoteAverage())
                .voteCount(domain.getVoteCount())
                .build();
    }
}
