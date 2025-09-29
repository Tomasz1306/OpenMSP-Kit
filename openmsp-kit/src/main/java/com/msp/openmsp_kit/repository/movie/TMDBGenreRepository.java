package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.TMDBGenreEntity;
import com.msp.openmsp_kit.model.persistence.entity.TMDBMovieEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TMDBGenreRepository extends JpaRepository<TMDBGenreEntity, Long> {
}
