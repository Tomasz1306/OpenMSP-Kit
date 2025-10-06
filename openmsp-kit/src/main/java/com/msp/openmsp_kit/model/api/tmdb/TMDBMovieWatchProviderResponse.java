package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TMDBMovieWatchProviderResponse(
        String link,
        List<Provider> ads,
        List<Provider> buy,
        List<Provider> rent,
        List<Provider> flatrate
) {
    public record Provider (
            String type,
            @JsonProperty("provider_id")
            int providerId,
            int movieId
    )  {}
}


