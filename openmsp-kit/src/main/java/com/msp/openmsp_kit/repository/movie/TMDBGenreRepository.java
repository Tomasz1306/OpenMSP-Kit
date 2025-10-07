package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBGenreEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface TMDBGenreRepository extends JpaRepository<TMDBGenreEntity, Long> {
    Optional<TMDBGenreEntity> findByTmdbIdAndName(Long tmdbId, String name);
}
