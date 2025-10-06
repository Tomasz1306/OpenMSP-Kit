package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TMDBWatchProviderResponse(
        @JsonProperty("logo_path")
        String logoPath,
        @JsonProperty("provider_id")
        int providerId,
        @JsonProperty("provider_name")
        String providerName,
        @JsonProperty("display_priority")
        int displayPriority
) {
}
