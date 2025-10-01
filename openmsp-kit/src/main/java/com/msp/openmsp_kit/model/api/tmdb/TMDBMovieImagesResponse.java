package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TMDBMovieImagesResponse(
    @JsonProperty("backdrops")
    List<TMDBImageResponse> backDrops,
    @JsonProperty("posters")
    List<TMDBImageResponse> posters,
    @JsonProperty("logos")
    List<TMDBImageResponse> logos
) {

}
