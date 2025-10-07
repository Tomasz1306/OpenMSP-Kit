package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TMDBMovieVideosResponse(
        @JsonProperty("results")
        List<TMDBMovieVideoResponse> results
) {
}
