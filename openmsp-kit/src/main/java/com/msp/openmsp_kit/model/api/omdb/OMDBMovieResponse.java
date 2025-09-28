package com.msp.openmsp_kit.model.api.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OMDBMovieResponse(
        @JsonProperty("Title")
        String title,
        @JsonProperty("Year")
        String year,
        @JsonProperty("Rated")
        String rated,
        @JsonProperty("Released")
        String releaseDate,
        @JsonProperty("Runtime")
        String runtime,
        @JsonProperty("Genre")
        String genres,
        @JsonProperty("Director")
        String director,
        @JsonProperty("Actors")
        String actors,
        @JsonProperty("Plot")
        String plot,
        @JsonProperty("Language")
        String language,
        @JsonProperty("Country")
        String country,
        @JsonProperty("Awards")
        String awards,
        @JsonProperty("Poster")
        String poster,
        @JsonProperty("Ratings")
        List<String> ratings,
        @JsonProperty("Metascore")
        String metascore,
        @JsonProperty("imdbRating")
        String imdbRating,
        @JsonProperty("imdbVotes")
        String imdbVotes,
        @JsonProperty("imdbID")
        String imdbId,
        @JsonProperty("Type")
        String type,
        @JsonProperty("DVD")
        String dvd,
        @JsonProperty("BoxOffice")
        String boxOffice,
        @JsonProperty("Production")
        String production,
        @JsonProperty("Website")
        String website
) {
}
