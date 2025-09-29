package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBGenreResponse(
        @JsonProperty("id")
        int id,
        @JsonProperty("name")
        String name
) {}
