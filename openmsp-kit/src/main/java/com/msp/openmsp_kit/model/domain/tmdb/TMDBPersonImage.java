package com.msp.openmsp_kit.model.domain.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBPersonImage {
    private double aspectRatio;
    private int height;
    private String iso6391;
    private String filePath;
    private double voteAverage;
    private int voteCount;
    private int width;
    private int id;
    private String type;
    private int personId;
}
