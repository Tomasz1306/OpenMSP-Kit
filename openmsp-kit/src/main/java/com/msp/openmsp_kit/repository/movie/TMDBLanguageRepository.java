package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBSpokenLanguageEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface TMDBLanguageRepository extends JpaRepository<TMDBSpokenLanguageEntity, Long> {
    Optional<TMDBSpokenLanguageEntity> findByIso6391(String iso_639_1);
}
