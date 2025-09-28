package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msp.openmsp_kit.model.domain.movie.Genre;

import java.util.List;

public record TMDBGenresResponse(
        @JsonProperty("genres")
        List<Genre> genres
) {}
