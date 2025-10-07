package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public record TMDBImageResponse(
    @JsonProperty("aspect_ratio")
    double aspectRatio,
    @JsonProperty("height")
    int height,
    @JsonProperty("iso_639_1")
    String iso_639_1,
    @JsonProperty("file_path")
    String filePath,
    @JsonProperty("vote_average")
    double voteAverage,
    @JsonProperty("vote_count")
    int voteCount,
    @JsonProperty("width")
    int width,
    String type,
    int tmdbId
) {
}
