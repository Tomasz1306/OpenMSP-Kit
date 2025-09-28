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
@Table(name = "spoken_languages")
public class SpokenLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "english_name")
    private String englishName;
    @Column(name = "iso_639_1")
    private String iso_639_1;
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "movies")
    private Set<TMDBMovieEntity> tmdbMovies;
}
