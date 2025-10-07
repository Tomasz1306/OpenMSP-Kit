package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBVideoEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TMDBVideoRepository extends JpaRepository<TMDBVideoEntity, Long> {
}
