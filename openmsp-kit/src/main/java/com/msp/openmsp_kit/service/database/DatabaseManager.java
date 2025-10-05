package com.msp.openmsp_kit.service.database;

import com.msp.openmsp_kit.model.api.tmdb.*;
import com.msp.openmsp_kit.model.domain.movie.*;
import com.msp.openmsp_kit.model.mapper.*;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.persistence.entity.*;
import com.msp.openmsp_kit.repository.movie.*;
import com.msp.openmsp_kit.service.metrics.MetricsCollector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Semaphore;

@Service
public class DatabaseManager {

    private final TMDBMovieRepository tmdbMovieRepository;
    private final TMDBLanguageRepository tmdbLanguageRepository;
    private final TMDBCompanyRepository tmdbCompanyRepository;
    private final TMDBCountryRepository tmdbCountryRepository;
    private final TMDBGenreRepository tmdbGenreRepository;
    private final TMDBImageRepository tmdbImageRepository;
    private final TMDBMovieMapper tmdbMovieMapper;
    private final TMDBGenreMapper tmdbGenreMapper;
    private final TMDBImageMapper tmdbImageMapper;
    private final TMDBProductionCompanyMapper tmdbProductionCompanyMapper;
    private final TMDBProductionCountryMapper tmdbProductionCountryMapper;
    private final TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper;
    private final MetricsCollector metricsCollector;

    private final Semaphore semaphore;

    public DatabaseManager(TMDBMovieRepository tmdbMovieRepository,
                           TMDBLanguageRepository tmdbLanguageRepository,
                           TMDBCompanyRepository tmdbCompanyRepository,
                           TMDBCountryRepository tmdbCountryRepository,
                           TMDBGenreRepository tmdbGenreRepository,
                           TMDBImageRepository tmdbImageRepository,
                           TMDBMovieMapper tmdbMovieMapper,
                           TMDBGenreMapper tmdbGenreMapper,
                           TMDBImageMapper tmdbImageMapper,
                           TMDBProductionCompanyMapper tmdbProductionCompanyMapper,
                           TMDBProductionCountryMapper tmdbProductionCountryMapper,
                           TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper,
                           MetricsCollector metricsCollector
                           ) {
        this.tmdbMovieRepository = tmdbMovieRepository;
        this.tmdbLanguageRepository = tmdbLanguageRepository;
        this.tmdbCompanyRepository = tmdbCompanyRepository;
        this.tmdbCountryRepository = tmdbCountryRepository;
        this.tmdbGenreRepository = tmdbGenreRepository;
        this.tmdbImageRepository = tmdbImageRepository;
        this.tmdbMovieMapper = tmdbMovieMapper;
        this.tmdbGenreMapper = tmdbGenreMapper;
        this.tmdbImageMapper = tmdbImageMapper;
        this.tmdbProductionCompanyMapper = tmdbProductionCompanyMapper;
        this.tmdbProductionCountryMapper = tmdbProductionCountryMapper;
        this.tmdbSpokenLanguageMapper = tmdbSpokenLanguageMapper;
        this.metricsCollector = metricsCollector;

        this.semaphore = new Semaphore(10);
    }

    public void saveEntities(List<Result<?>> results) {
        for (Result<?> result : results) {
            saveEntity(result);
        }
    }
    @Transactional
    public void saveEntity(Result<?> result) {
        Object entity = result.data();
        try {
            this.semaphore.acquire();
            if (entity instanceof TMDBMovieImpl) {
                TMDBMovieEntity movieEntity = tmdbMovieMapper.toEntityFromDomain((TMDBMovieImpl) entity);
                Set<TMDBGenreEntity> genreEntities = new HashSet<>();
                for(TMDBGenre genre : ((TMDBMovieImpl) entity).getGenres()) {
                    Optional<TMDBGenreEntity> genreEntity = tmdbGenreRepository.findByTmdbIdAndName((long) genre.getId(), genre.getName());
                    genreEntity.ifPresent(genreEntities::add);
                }
                movieEntity.setGenres(genreEntities);

                Set<TMDBSpokenLanguageEntity> spokenLanguages = new HashSet<>();
                for (TMDBSpokenLanguage spokenLanguage : ((TMDBMovieImpl) entity).getSpokenLanguages()) {
                    Optional<TMDBSpokenLanguageEntity> spokenLanguageEntity = tmdbLanguageRepository.findByIso6391(spokenLanguage.getIso_639_1());
                    spokenLanguageEntity.ifPresent(spokenLanguages::add);
                }
                movieEntity.setSpokenLanguages(spokenLanguages);

                Set<TMDBProductionCountryEntity> productionCountries = new HashSet<>();
                for (TMDBProductionCountry productionCountry : ((TMDBMovieImpl) entity).getProductionCountries()) {
                    Optional<TMDBProductionCountryEntity> productionCountryEntity = tmdbCountryRepository.findByIso31661(productionCountry.getIso_3166_1());
                    productionCountryEntity.ifPresent(productionCountries::add);
                }
                movieEntity.setProductionCountries(productionCountries);

                tmdbMovieRepository.save(movieEntity);
                metricsCollector.incrementTotalDatabaseSaves();
            } else
            if (entity instanceof TMDBGenre) {
                tmdbGenreRepository.save(tmdbGenreMapper.toEntityFromDomain((TMDBGenre) entity));
                metricsCollector.incrementTotalDatabaseSaves();
            } else
            if (entity instanceof TMDBProductionCountry) {
                tmdbCountryRepository.save(tmdbProductionCountryMapper.toEntityFromDomain((TMDBProductionCountry) entity));
                metricsCollector.incrementTotalDatabaseSaves();
            } else
            if (entity instanceof TMDBProductionCompany) {
                tmdbCompanyRepository.save(tmdbProductionCompanyMapper.toEntityFromDomain((TMDBProductionCompany) entity));
                metricsCollector.incrementTotalDatabaseSaves();
            } else
            if (entity instanceof TMDBSpokenLanguage) {
                tmdbLanguageRepository.save(tmdbSpokenLanguageMapper.toEntityFromDomain((TMDBSpokenLanguage) entity));
                metricsCollector.incrementTotalDatabaseSaves();
            } else
            if (entity instanceof TMDBImageResponse) {
                tmdbImageRepository.save(tmdbImageMapper.toEntityFromApi((TMDBImageResponse) entity));
                metricsCollector.incrementTotalDatabaseSaves();
            }

        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
