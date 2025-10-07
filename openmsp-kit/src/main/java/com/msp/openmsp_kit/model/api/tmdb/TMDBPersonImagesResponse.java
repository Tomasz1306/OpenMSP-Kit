package com.msp.openmsp_kit.model.api.tmdb;

import java.util.List;

public record TMDBPersonImagesResponse(
        List<TMDBImageResponse> profiles
) {
}
