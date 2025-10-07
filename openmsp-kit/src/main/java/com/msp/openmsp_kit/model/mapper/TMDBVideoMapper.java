package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieVideoResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBMovieVideo;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBVideoEntity;
import org.springframework.stereotype.Component;

@Component
public class TMDBVideoMapper implements ToEntityFromApiMapper<TMDBVideoEntity, TMDBMovieVideoResponse>,
        ToEntityFromDomainMapper<TMDBVideoEntity, TMDBMovieVideo>, ToDomainFromApiMapper<TMDBMovieVideo, TMDBMovieVideoResponse>{
    @Override
    public TMDBMovieVideo toDomainFromApi(TMDBMovieVideoResponse response) {
        return TMDBMovieVideo
                .builder()
                .tmdbVideoId(response.id())
                .name(response.name())
                .site(response.site())
                .key(response.key())
                .iso6391(response.iso6391())
                .iso31661(response.iso31661())
                .publishedAt(response.publishedAt())
                .official(response.official())
                .type(response.type())
                .build();
    }

    @Override
    public TMDBVideoEntity toEntityFromApi(TMDBMovieVideoResponse domain) {
        return null;
    }

    @Override
    public TMDBVideoEntity toEntityFromDomain(TMDBMovieVideo domain) {
        return null;
    }
}
