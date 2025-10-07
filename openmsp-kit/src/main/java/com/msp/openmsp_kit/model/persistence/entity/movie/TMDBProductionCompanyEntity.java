package com.msp.openmsp_kit.model.persistence.entity.movie;

import com.msp.openmsp_kit.model.persistence.entity.AbstractEntity;
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
@Table(name = "production_companies")
public class TMDBProductionCompanyEntity extends AbstractEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "headquarters")
    private String headquarters;

    @Column(name = "homepage")
    private String homepage;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "name")
    private String name;

    @Column(name = "origin_country")
    private String originCountry;

    @Column(name = "parent_company")
    private String parentCompany;

    @ManyToMany(mappedBy = "productionCompanies")
    private Set<TMDBMovieEntity> tmdbMovies;
}
