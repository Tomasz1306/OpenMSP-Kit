package com.msp.openmsp_kit.model.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBGenre {
    private int id;
    private int tmdbId;
    private String iso_639_1;
    private String name;
}
