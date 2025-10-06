package com.msp.openmsp_kit.model.api.tmdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msp.openmsp_kit.model.domain.movie.TMDBGenre;
import com.msp.openmsp_kit.model.domain.movie.TMDBProductionCompany;
import com.msp.openmsp_kit.model.domain.movie.TMDBProductionCountry;
import com.msp.openmsp_kit.model.domain.movie.TMDBSpokenLanguage;

import java.util.List;

public record TMDBMovieDetailsResponse(
        @JsonProperty("adult")
        boolean adult,
        @JsonProperty("backdrop_path")
        String backdropPath,
        @JsonProperty("belongs_to_collections")
        String belongsToCollection,
        @JsonProperty("budget")
        int budget,
        @JsonProperty("homepage")
        String homepage,
        @JsonProperty("id")
        int tmdbId,
        @JsonProperty("imdb_id")
        String imdbId,
        @JsonProperty("original_language")
        String originalLanguage,
        @JsonProperty("original_title")
        String originalTitle,
        @JsonProperty("overview")
        String overview,
        @JsonProperty("popularity")
        double popularity,
        @JsonProperty("poster_path")
        String posterPath,
        @JsonProperty("release_date")
        String releaseDate,
        @JsonProperty("revenue")
        long revenue,
        @JsonProperty("runtime")
        int runtime,
        @JsonProperty("status")
        String status,
        @JsonProperty("tagline")
        String tagline,
        @JsonProperty("title")
        String title,
        @JsonProperty("video")
        boolean video,
        @JsonProperty("vote_average")
        double voteAverage,
        @JsonProperty("vote_count")
        int voteCount,
        @JsonProperty("genres")
        List<TMDBGenreResponse> genres,
        @JsonProperty("production_companies")
        List<TMDBProductionCompany> productionCompanies,
        @JsonProperty("production_countries")
        List<TMDBCountryResponse> productionCountries,
        @JsonProperty("spoken_languages")
        List<TMDBLanguageResponse> spokenLanguages
) {
}
