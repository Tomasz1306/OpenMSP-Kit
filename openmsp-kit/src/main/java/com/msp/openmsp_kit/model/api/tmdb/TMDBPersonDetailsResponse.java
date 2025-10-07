package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record TMDBPersonDetailsResponse(
        boolean adult,
        @JsonProperty("also_known_as")
        List<String> alsoKnownAs,
        String biography,
        String birthday,
        String deathday,
        int gender,
        String homepage,
        @JsonProperty("id")
        int tmdbId,
        @JsonProperty("imdb_id")
        String imdbId,
        @JsonProperty("known_for_department")
        String knownForDepartment,
        String name,
        @JsonProperty("place_of_birth")
        String placeOfBirth,
        double popularity,
        @JsonProperty("profile_path")
        String profilePath
) {
}
