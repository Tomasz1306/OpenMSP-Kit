package com.msp.openmsp_kit.model.api.tmdb;

import java.util.Map;

public record TMDBMovieWatchProvidersResponse(
        Map<String, TMDBMovieWatchProviderResponse> results
) {
}
