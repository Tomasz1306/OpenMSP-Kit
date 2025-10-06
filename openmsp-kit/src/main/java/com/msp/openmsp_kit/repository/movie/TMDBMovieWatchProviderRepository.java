package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.TMDBMovieProviderEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TMDBMovieWatchProviderRepository extends JpaRepository<TMDBMovieProviderEntity, Integer> {
}
