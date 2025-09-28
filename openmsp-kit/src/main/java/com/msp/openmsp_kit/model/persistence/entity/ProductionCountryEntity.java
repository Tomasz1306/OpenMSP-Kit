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
@Table(name = "production_countries")
public class ProductionCountryEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="iso_3166_1")
    private String iso_3166_1;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "movies")
    private Set<TMDBMovieEntity> tmdbMovies;
}
