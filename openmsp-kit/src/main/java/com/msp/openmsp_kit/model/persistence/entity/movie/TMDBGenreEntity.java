package com.msp.openmsp_kit.model.persistence.entity.movie;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msp.openmsp_kit.model.persistence.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(name = "tmdb_genres")
public class TMDBGenreEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tmdb_id")
    private Long tmdbId;

    @Column(name = "iso_639_1")
    private String iso_639_1;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "genres",  fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TMDBMovieEntity> tmdbMovies;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ",tmdbId='" + tmdbId + '\'' +
                ",iso_639_1'" + iso_639_1 +
                ",name='" + name + '\'' +
                '}';
    }
}
