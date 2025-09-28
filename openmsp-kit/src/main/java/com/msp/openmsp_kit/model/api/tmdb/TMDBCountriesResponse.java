package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBCountriesResponse(
        @JsonProperty("iso_3166_1")
        String iso_3166_1,
        @JsonProperty("english_name")
        String english_name,
        @JsonProperty("native_name")
        String native_name
) {
}
