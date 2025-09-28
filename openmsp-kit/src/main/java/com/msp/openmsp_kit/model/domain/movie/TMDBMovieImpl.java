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
    private List<Genre> genres;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountries;
    private List<SpokenLanguage> spokenLanguages;
}
