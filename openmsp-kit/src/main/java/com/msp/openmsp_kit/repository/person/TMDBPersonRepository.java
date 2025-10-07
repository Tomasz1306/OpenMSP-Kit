package com.msp.openmsp_kit.repository.person;

import com.msp.openmsp_kit.model.persistence.entity.person.TMDBPersonEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TMDBPersonRepository extends JpaRepository<TMDBPersonEntity, Long> {
}
