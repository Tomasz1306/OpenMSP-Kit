package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBImage;
import com.msp.openmsp_kit.model.persistence.entity.TMDBImageEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBImageMapper implements ToEntityFromDomainMapper<TMDBImageEntity, TMDBImage>,
        ToEntityFromApiMapper<TMDBImageEntity, TMDBImageResponse>, ToDomainFromApiMapper<TMDBImage, TMDBImageResponse>{
    @Override
    public TMDBImage toDomainFromApi(TMDBImageResponse response) {
        return TMDBImage
                .builder()
                .type(response.getType())
                .width(response.getWidth())
                .height(response.getHeight())
                .aspect_ratio(response.getAspectRatio())
                .file_path(response.getFilePath())
                .iso_639_1(response.getIso_639_1())
                .vote_average(response.getVoteAverage())
                .vote_count(response.getVoteCount())
                .build();
    }

    @Override
    public TMDBImageEntity toEntityFromApi(TMDBImageResponse domain) {
        return TMDBImageEntity
                .builder()
                .type(domain.getType())
                .width(domain.getWidth())
                .height(domain.getHeight())
                .aspectRatio(domain.getAspectRatio())
                .filePath(domain.getFilePath())
                .iso_639_1(domain.getIso_639_1())
                .voteAverage(domain.getVoteAverage())
                .voteCount(domain.getVoteCount())
                .build();
    }

    @Override
    public TMDBImageEntity toEntityFromDomain(TMDBImage domain) {
        return TMDBImageEntity
                .builder()
                .type(domain.getType())
                .width(domain.getWidth())
                .height(domain.getHeight())
                .aspectRatio(domain.getAspect_ratio())
                .filePath(domain.getFile_path())
                .iso_639_1(domain.getIso_639_1())
                .voteAverage(domain.getVote_average())
                .voteCount(domain.getVote_count())
                .build();
    }
}
