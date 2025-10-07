package com.msp.openmsp_kit.model.domain.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBMovieVideo {
    private long tmdbMovieId;
    private String iso6391;
    private String iso31661;
    private String name;
    private String key;
    private String site;
    private int size;
    private String type;
    private boolean official;
    private String publishedAt;
    private String tmdbVideoId;
}
