package com.msp.openmsp_kit.model.persistence.entity.movie;

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
@Table(name = "watch_providers")
public class TMDBWatchProviderEntity {
    @Id
    @Column(name = "provider_id")
    private Long providerId;

    @Column(name = "provider_name")
    private String providerName;
    @Column(name = "logo_paht")
    private String logoPath;
    @Column(name = "display_priority")
    private Long displayPriority;
    @Column(name = "iso_3166_1")
    private String iso31661;

    @OneToMany(mappedBy = "tmdbWatchProvider")
    private Set<TMDBMovieProviderEntity> movieProviders;

    @Override
    public String toString() {
        return "{" +
                "providerId=`" + providerId + '\'' +
                "providerName=`" + providerName + '\'' +
                "logoPath=`" + logoPath + '\'' +
                "displayPriority=`" + displayPriority + '\'' +
                "iso_31661=`" + iso31661 + '\'';
    }
}
