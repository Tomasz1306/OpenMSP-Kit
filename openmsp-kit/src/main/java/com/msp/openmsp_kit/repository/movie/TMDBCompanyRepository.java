package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.TMDBMovieEntity;
import com.msp.openmsp_kit.model.persistence.entity.TMDBProductionCompanyEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TMDBCompanyRepository extends JpaRepository<TMDBProductionCompanyEntity, Long> {
}
