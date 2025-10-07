package com.msp.openmsp_kit.model.domain.tmdb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBMovieWatchProvider {
    private String link;
    private String type;
    private int providerId;
    private int movieId;
    private String iso_639_1;
}
