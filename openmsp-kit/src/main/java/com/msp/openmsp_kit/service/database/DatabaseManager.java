package com.msp.openmsp_kit.service.database;

import com.msp.openmsp_kit.model.api.tmdb.*;
import com.msp.openmsp_kit.model.domain.tmdb.*;
import com.msp.openmsp_kit.model.mapper.*;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.persistence.entity.movie.*;
import com.msp.openmsp_kit.model.persistence.entity.person.TMDBPersonEntity;
import com.msp.openmsp_kit.repository.movie.*;
import com.msp.openmsp_kit.repository.person.TMDBPersonRepository;
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
    private final TMDBWatchProviderRepository tmdbWatchProviderRepository;
    private final TMDBVideoRepository tmdbVideoRepository;
    private final TMDBPersonRepository tmdbPersonRepository;

    private final TMDBMovieMapper tmdbMovieMapper;
    private final TMDBGenreMapper tmdbGenreMapper;
    private final TMDBImageMapper tmdbImageMapper;
    private final TMDBProductionCompanyMapper tmdbProductionCompanyMapper;
    private final TMDBProductionCountryMapper tmdbProductionCountryMapper;
    private final TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper;
    private final TMDBWatchProviderMapper tmdbWatchProviderMapper;
    private final TMDBPersonMapper tmdbPersonMapper;

    private final MetricsCollector metricsCollector;

    private final Semaphore semaphore;
    private final TMDBMovieWatchProviderRepository tMDBMovieWatchProviderRepository;

    public DatabaseManager(TMDBMovieRepository tmdbMovieRepository,
                           TMDBLanguageRepository tmdbLanguageRepository,
                           TMDBCompanyRepository tmdbCompanyRepository,
                           TMDBCountryRepository tmdbCountryRepository,
                           TMDBGenreRepository tmdbGenreRepository,
                           TMDBImageRepository tmdbImageRepository,
                           TMDBWatchProviderRepository tmdbWatchProviderRepository,
                           TMDBVideoRepository tmdbVideoRepository,
                           TMDBPersonRepository tmdbPersonRepository,
                           TMDBMovieMapper tmdbMovieMapper,
                           TMDBGenreMapper tmdbGenreMapper,
                           TMDBImageMapper tmdbImageMapper,
                           TMDBProductionCompanyMapper tmdbProductionCompanyMapper,
                           TMDBProductionCountryMapper tmdbProductionCountryMapper,
                           TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper,
                           TMDBWatchProviderMapper tmdbWatchProviderMapper,
                           TMDBPersonMapper tmdbPersonMapper,
                           MetricsCollector metricsCollector,
                           TMDBMovieWatchProviderRepository tMDBMovieWatchProviderRepository) {
        this.tmdbMovieRepository = tmdbMovieRepository;
        this.tmdbLanguageRepository = tmdbLanguageRepository;
        this.tmdbCompanyRepository = tmdbCompanyRepository;
        this.tmdbCountryRepository = tmdbCountryRepository;
        this.tmdbGenreRepository = tmdbGenreRepository;
        this.tmdbImageRepository = tmdbImageRepository;
        this.tmdbWatchProviderRepository = tmdbWatchProviderRepository;
        this.tmdbVideoRepository = tmdbVideoRepository;
        this.tmdbPersonRepository = tmdbPersonRepository;

        this.tmdbMovieMapper = tmdbMovieMapper;
        this.tmdbGenreMapper = tmdbGenreMapper;
        this.tmdbImageMapper = tmdbImageMapper;
        this.tmdbProductionCompanyMapper = tmdbProductionCompanyMapper;
        this.tmdbProductionCountryMapper = tmdbProductionCountryMapper;
        this.tmdbSpokenLanguageMapper = tmdbSpokenLanguageMapper;
        this.tmdbWatchProviderMapper = tmdbWatchProviderMapper;
        this.tmdbPersonMapper = tmdbPersonMapper;

        this.metricsCollector = metricsCollector;

        this.semaphore = new Semaphore(10);
        this.tMDBMovieWatchProviderRepository = tMDBMovieWatchProviderRepository;
    }

    public void saveEntities(List<Result<?>> results) {
        for (Result<?> result : results) {
            saveEntity(result);
        }
    }

    //TODO implements error handling
    @Transactional
    public Result<?> saveEntity(Result<?> result) {
        Object data = result.data();
        try {
            this.semaphore.acquire();
            boolean success = false;
            if (data instanceof TMDBMovie) {
                success = saveTMDBMovie((TMDBMovie) data);
            } else if (data instanceof TMDBGenre) {
                success = saveTMDBGenre((TMDBGenre) data);
            } else if (data instanceof TMDBProductionCountry) {
                success = saveTMDBProductionCountry((TMDBProductionCountry) data);
            } else if (data instanceof TMDBProductionCompany) {
                success = saveTMDBProductionCompany((TMDBProductionCompany) data);
            } else if (data instanceof TMDBSpokenLanguage) {
                success = saveTMDBSpokenLanguage((TMDBSpokenLanguage) data);
            } else if (data instanceof TMDBImageResponse) {
                success = saveTMDBImage((TMDBImageResponse) data);
            } else if (data instanceof TMDBWatchProvider) {
                success = saveTMDBWatchProvider((TMDBWatchProvider) data);
            } else if (data instanceof TMDBMovieWatchProvider) {
                success = saveTMDBMovieWatchProvider((TMDBMovieWatchProvider) data);
            } else if (data instanceof TMDBMovieVideo) {
                success = saveTMDBMovieVideo((TMDBMovieVideo) data);
            } else if (data instanceof TMDBPerson) {
                success = saveTMDBPerson((TMDBPerson) data);
            }
            if (success) {
                return null;
            } else {
                return result;
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        return result;
    }
    private boolean saveTMDBMovie(TMDBMovie domain) {
        TMDBMovieEntity movieEntity = tmdbMovieMapper.toEntityFromDomain((TMDBMovie) domain);
        Set<TMDBGenreEntity> genreEntities = new HashSet<>();
        for(TMDBGenre genre : ((TMDBMovie) domain).getGenres()) {
            Optional<TMDBGenreEntity> genreEntity = tmdbGenreRepository.findByTmdbIdAndName((long) genre.getTmdbId(), genre.getName());
            genreEntity.ifPresent(genreEntities::add);
        }
        movieEntity.setGenres(genreEntities);

        Set<TMDBSpokenLanguageEntity> spokenLanguages = new HashSet<>();
        for (TMDBSpokenLanguage spokenLanguage : ((TMDBMovie) domain).getSpokenLanguages()) {
            Optional<TMDBSpokenLanguageEntity> spokenLanguageEntity = tmdbLanguageRepository.findByIso6391(spokenLanguage.getIso_639_1());
            spokenLanguageEntity.ifPresent(spokenLanguages::add);
        }
        movieEntity.setSpokenLanguages(spokenLanguages);

        Set<TMDBProductionCountryEntity> productionCountries = new HashSet<>();
        for (TMDBProductionCountry productionCountry : ((TMDBMovie) domain).getProductionCountries()) {
            Optional<TMDBProductionCountryEntity> productionCountryEntity = tmdbCountryRepository.findByIso31661(productionCountry.getIso_3166_1());
            productionCountryEntity.ifPresent(productionCountries::add);
        }
        movieEntity.setProductionCountries(productionCountries);

        tmdbMovieRepository.saveAndFlush(movieEntity);
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBGenre(TMDBGenre domain) {
        tmdbGenreRepository.save(tmdbGenreMapper.toEntityFromDomain((TMDBGenre) domain));
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBProductionCountry(TMDBProductionCountry domain) {
        tmdbCountryRepository.save(tmdbProductionCountryMapper.toEntityFromDomain((TMDBProductionCountry) domain));
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBProductionCompany(TMDBProductionCompany domain) {
        tmdbCompanyRepository.save(tmdbProductionCompanyMapper.toEntityFromDomain((TMDBProductionCompany) domain));
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    protected boolean saveTMDBSpokenLanguage(TMDBSpokenLanguage domain) {
        tmdbLanguageRepository.save(tmdbSpokenLanguageMapper.toEntityFromDomain((TMDBSpokenLanguage) domain));
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBImage(TMDBImageResponse response) {
        TMDBMovieEntity movieEntity = tmdbMovieRepository.findByTmdbIdAndIso6391(((TMDBImageResponse) response).getTmdbId(), ((TMDBImageResponse) response).getIso_639_1());
        if (movieEntity == null) {
            return false;
        }
        TMDBImageEntity imageEntity = tmdbImageMapper.toEntityFromApi((TMDBImageResponse) response);
        imageEntity.setMovie(movieEntity);
        tmdbImageRepository.save(imageEntity);
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBWatchProvider(TMDBWatchProvider domain) {
        tmdbWatchProviderRepository.save(tmdbWatchProviderMapper.toEntityFromDomain((TMDBWatchProvider) domain));
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBMovieWatchProvider(TMDBMovieWatchProvider domain) {
        TMDBMovieEntity movieEntity = tmdbMovieRepository.findByTmdbIdAndIso6391(((TMDBMovieWatchProvider) domain).getMovieId(), ((TMDBMovieWatchProvider) domain).getIso_639_1());
        if (movieEntity == null) {
            return false;
        }
        TMDBWatchProviderEntity watchProviderEntity = tmdbWatchProviderRepository.findByProviderId((long) ((TMDBMovieWatchProvider) domain).getProviderId());
        TMDBMovieProviderEntity movieProviderEntity = TMDBMovieProviderEntity
                .builder()
                .tmdbWatchProvider(watchProviderEntity)
                .tmdbMovie(movieEntity)
                .link(((TMDBMovieWatchProvider) domain).getLink())
                .type(((TMDBMovieWatchProvider) domain).getType())
                .build();
        tMDBMovieWatchProviderRepository.save(movieProviderEntity);
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBMovieVideo(TMDBMovieVideo domain) {
        TMDBMovieEntity movieEntity = tmdbMovieRepository.findByTmdbIdAndIso6391((int) domain.getTmdbMovieId(), domain.getIso6391());
        if (movieEntity == null) {
            return false;
        }

        //TODO use mapper
        TMDBVideoEntity videoEntity = TMDBVideoEntity
                .builder()
                .tmdbMovie(movieEntity)
                .tmdbVideoId(domain.getTmdbVideoId())
                .iso6391(domain.getIso6391())
                .name(domain.getName())
                .type(domain.getType())
                .key(domain.getKey())
                .size(domain.getSize())
                .iso31661(domain.getIso31661())
                .site(domain.getSite())
                .official(domain.isOfficial())
                .publishedAt(domain.getPublishedAt())
                .build();

        tmdbVideoRepository.save(videoEntity);
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }

    private boolean saveTMDBPerson(TMDBPerson domain) {
        TMDBPersonEntity personEntity = tmdbPersonMapper.toEntityFromDomain(domain);
        tmdbPersonRepository.save(personEntity);
        metricsCollector.incrementTotalDatabaseSaves();
        return true;
    }
}
