package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBMovieGenreResponse(
        @JsonProperty("id")
        int tmdbId,
        @JsonProperty("name")
        String name
) {}
