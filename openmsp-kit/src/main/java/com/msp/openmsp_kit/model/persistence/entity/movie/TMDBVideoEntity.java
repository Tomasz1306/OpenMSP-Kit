package com.msp.openmsp_kit.model.persistence.entity.movie;

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
@Table(name = "tmdb_videos")
public class TMDBVideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iso_639_1")
    private String iso6391;
    @Column(name = "iso_3166_1")
    private String iso31661;
    private String name;
    private String key;
    private String site;
    private int size;
    private String type;
    private boolean official;
    @Column(name = "published_at")
    private String publishedAt;
    private String tmdbVideoId;

    @ManyToOne
    @JoinColumn(name = "tmdb_movie_id")
    private TMDBMovieEntity tmdbMovie;
}
