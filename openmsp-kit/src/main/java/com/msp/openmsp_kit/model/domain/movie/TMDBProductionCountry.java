package com.msp.openmsp_kit.model.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBProductionCountry {
    private String iso_3166_1;
    private String englishName;
    private String nativeName;
}
