package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TMDBImageResponse {
    @JsonProperty("aspect_ratio")
    private double aspectRatio;
    @JsonProperty("height")
    private int height;
    @JsonProperty("iso_639_1")
    private String iso_639_1;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("width")
    private int width;
    private String type;
    private int tmdbId;
}
