package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBMovieId(
        @JsonProperty("id")
        int id
) {
}
