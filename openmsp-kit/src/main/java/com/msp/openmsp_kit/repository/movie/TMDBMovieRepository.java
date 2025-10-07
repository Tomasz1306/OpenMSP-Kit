package com.msp.openmsp_kit.repository.movie;

import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBMovieEntity;
import jakarta.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface TMDBMovieRepository extends JpaRepository<TMDBMovieEntity, Long> {

    List<TMDBMovieEntity> findAllByTmdbId(Integer movieId);
    TMDBMovieEntity findByTmdbIdAndIso6391(Integer movieId, String iso_639_1);
}
