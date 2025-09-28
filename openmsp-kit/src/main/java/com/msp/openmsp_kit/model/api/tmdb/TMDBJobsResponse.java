package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBJobsResponse(
        @JsonProperty("department")
        String department
) {}
