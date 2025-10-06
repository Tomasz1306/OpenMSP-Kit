package com.msp.openmsp_kit.model.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBMovieEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "iso_639_1")
    private String iso6391;
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
    @Column(name = "overview", length = 1000)
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
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_company_id")
    )
    @JsonIgnore
    private Set<TMDBProductionCompanyEntity> productionCompanies = null;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "MovieGenre",
            joinColumns = @JoinColumn(name = "tmdb_movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnore
    private Set<TMDBGenreEntity> genres;

    @ManyToMany
    @JoinTable(
            name = "MovieProductionCountry",
            joinColumns = @JoinColumn(name ="id"),
            inverseJoinColumns = @JoinColumn(name = "iso_3166_1")
    )
    @JsonIgnore
    private Set<TMDBProductionCountryEntity> productionCountries;

    @ManyToMany
    @JoinTable(
            name = "MovieSpokenLanguages",
            joinColumns = @JoinColumn(name ="id"),
            inverseJoinColumns = @JoinColumn(name = "iso_639_1")
    )
    @JsonIgnore
    private Set<TMDBSpokenLanguageEntity> spokenLanguages;

    @OneToMany(mappedBy = "tmdbMovie")
    private Set<TMDBMovieProviderEntity> movieProviders;

    @OneToMany(mappedBy = "movie")
    private List<TMDBImageEntity> images;


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", tmdbId=" + tmdbId +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", homepage='" + homepage + '\'' +
                ", overview='" + overview + '\'' +
                ", language='" + language + '\'' +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
