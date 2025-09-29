package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.TMDBMovieEntity;
import com.msp.openmsp_kit.model.persistence.entity.TMDBProductionCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TMDBCountryRepository extends JpaRepository<TMDBProductionCountryEntity, Long> {
}
