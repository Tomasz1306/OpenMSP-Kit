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
public class TMDBProductionCountryEntity extends AbstractEntity {

    @Id
    @Column(name ="iso_3166_1")
    private String iso31661;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "native_name")
    private String nativeName;

    @ManyToMany(mappedBy = "productionCountries")
    private Set<TMDBMovieEntity> tmdbMovies;
}
