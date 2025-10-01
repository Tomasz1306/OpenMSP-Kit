package com.msp.openmsp_kit.model.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;
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
    @Column(name = "ovierview", length = 1000)
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
    private Set<TMDBProductionCompanyEntity> productionCompanies;

    @ManyToMany
    @JoinTable(
            name = "MovieGenre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<TMDBGenreEntity> genres;

    @ManyToMany
    @JoinTable(
            name = "MovieProductionCountry",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "iso_3166_1")
    )
    private Set<TMDBProductionCountryEntity> productionCountries;

    @ManyToMany
    @JoinTable(
            name = "MovieSpokenLanguages",
            joinColumns = @JoinColumn(name ="movie_id"),
            inverseJoinColumns = @JoinColumn(name = "iso_639_1")
    )
    private Set<TMDBSpokenLanguageEntity> spokenLanguages;

    @OneToMany(mappedBy = "movie")
    private List<TMDBImageEntity> images;


    @Override
    public int hashCode() {
        return Objects.hash(tmdbId);
    }

}
