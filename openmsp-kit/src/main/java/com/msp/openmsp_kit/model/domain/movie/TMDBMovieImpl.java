package com.msp.openmsp_kit.model.domain.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TMDBMovieImpl implements Movie, MovieWithRatings {

    @JsonProperty("id")
    private Integer tmdbId;
    private String imdbId;

    private String iso_639_1;
    private String title;
    private String originalTitle;
    private String releaseDate;
    private String overview;
    private String language;
    private String posterPath;
    private String backdropPath;
    private String status;
    private String tagline;
    private String homepage;
    private boolean adult;
    private boolean video;
    private int budget;
    private int voteCount;
    private double voteAverage;
    private double popularity;
    private List<TMDBGenre> genres;
    private List<TMDBProductionCompany> productionCompanies;
    private List<TMDBProductionCountry> productionCountries;
    private List<TMDBSpokenLanguage> spokenLanguages;
}
