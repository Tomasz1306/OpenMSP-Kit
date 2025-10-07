package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBMovieVideoResponse(
        @JsonProperty("iso_639_1")
        String iso6391,
        @JsonProperty("iso_3166_1")
        String iso31661,
        String name,
        String key,
        String site,
        int size,
        String type,
        boolean official,
        @JsonProperty("published_at")
        String publishedAt,
        String id
) {
}
