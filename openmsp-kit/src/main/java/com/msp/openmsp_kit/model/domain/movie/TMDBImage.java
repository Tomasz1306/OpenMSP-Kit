package com.msp.openmsp_kit.model.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBImage {
    private double aspect_ratio;
    private int height;
    private String iso_639_1;
    private String file_path;
    private double vote_average;
    private int vote_count;
    private int width;
    private int id;
    private String type;
}
