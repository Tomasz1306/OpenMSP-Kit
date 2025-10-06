package com.msp.openmsp_kit.model.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tmdb_movie_watch_providers")
public class TMDBMovieProviderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tmdb_movie_id")
    private TMDBMovieEntity tmdbMovie;

    @ManyToOne
    @JoinColumn(name = "tmdb_watch_provider_id")
    private TMDBWatchProviderEntity tmdbWatchProvider;

    private String link;
    private String type;
}
