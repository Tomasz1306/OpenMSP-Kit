package com.msp.openmsp_kit.model.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SpokenLanguage {
    private String englishName;
    private String iso_639_1;
    private String name;
}
