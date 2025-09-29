package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBCompanyDetailsResponse(
        @JsonProperty("description")
        String description,
        @JsonProperty("headquarters")
        String headquarters,
        @JsonProperty("homepage")
        String homepage,
        @JsonProperty("id")
        Integer id,
        @JsonProperty("logo_path")
        String logoPath,
        @JsonProperty("name")
        String name,
        @JsonProperty("origin_country")
        String originCountry,
        @JsonProperty("parent_company")
        String parentCompany

) {
}
