package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBMovieImage;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBImageEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBMovieImageMapper implements ToEntityFromDomainMapper<TMDBImageEntity, TMDBMovieImage>,
        ToEntityFromApiMapper<TMDBImageEntity, TMDBImageResponse>, ToDomainFromApiMapper<TMDBMovieImage, TMDBImageResponse> {
    @Override
    public TMDBMovieImage toDomainFromApi(TMDBImageResponse response) {
        return TMDBMovieImage
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
    public TMDBImageEntity toEntityFromApi(TMDBImageResponse response) {
        return TMDBImageEntity
                .builder()
                .type(response.type())
                .width(response.width())
                .height(response.height())
                .aspectRatio(response.aspectRatio())
                .filePath(response.filePath())
                .iso_639_1(response.iso_639_1())
                .voteAverage(response.voteAverage())
                .voteCount(response.voteCount())
                .build();
    }

    @Override
    public TMDBImageEntity toEntityFromDomain(TMDBMovieImage domain) {
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