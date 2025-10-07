package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBWatchProviderEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TMDBWatchProviderRepository extends JpaRepository<TMDBWatchProviderEntity, Long> {
    TMDBWatchProviderEntity findByProviderId(Long providerId);
}
