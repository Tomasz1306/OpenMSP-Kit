package com.msp.openmsp_kit.model.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tmdb_movies")
public class TMDBMovieEntity extends AbstractEntity {

    @Id
    @Column(name = "tmdb_id")
    private Integer tmdbId;

    @Column(name = "imdb_id")
    private String imdbId;
    @Column(name = "title")
    private String title;
    @Column(name = "original_title")
    private String originalTitle;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "homepage")
    private String homepage;
    @Column(name = "ovierview")
    private String overview;
    @Column(name = "language")
    private String language;
    @Column(name = "poster_path")
    private String posterPath;
    @Column(name = "adult")
    private boolean adult;
    @Column(name = "video")
    private boolean video;
    @Column(name = "budget")
    private Integer budget;
    @Column(name ="vote_count")
    private Integer voteCount;
    @Column(name = "vote_average")
    private Double voteAverage;
    @Column(name = "popularity")
    private Double popularity;
    @Column(name = "backdrop_path")
    private String backdropPath;

    @JoinTable(
            name = "MovieProductionCompanies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "product_company_id")
    )
    private Set<TMDBProductionCompanyEntity> productionCompanies;

    @JoinTable(
            name = "MovieGenre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<TMDBGenreEntity> genres;

    @JoinTable(
            name = "MovieProductionCountry",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "production_country_id")
    )
    private Set<TMDBProductionCountryEntity> productionCountries;

    @JoinTable(
            name = "MovieSpokenLanguages",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "spoken_language_id")
    )
    private Set<TMDBSpokenLanguageEntity> spokenLanguages;
}
