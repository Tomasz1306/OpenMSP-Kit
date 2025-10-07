package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBProductionCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TMDBCountryRepository extends JpaRepository<TMDBProductionCountryEntity, Long> {
    Optional<TMDBProductionCountryEntity> findByIso31661(String iso_639_1);
}
