package com.msp.openmsp_kit.model.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tmdb_movie")
public class TMDBMovieEntity {

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

    @ManyToMany
    @JoinTable(
            name = "MovieProductionCompanies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "product_company_id")
    )
    private Set<ProductionCompanyEntity> productionCompanies;

    @ManyToMany
    @JoinTable(
            name = "MovieGenre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<GenreEntity> genres;

    @ManyToMany
    @JoinTable(
            name = "MovieProductionCountry",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "production_country_id")
    )
    private Set<ProductionCountryEntity> productionCountries;

    @ManyToMany
    @JoinTable(
            name = "MovieSpokenLanguages",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "spoken_language_id")
    )
    private Set<ProductionCountryEntity> spokenLanguages;
}
