package com.msp.openmsp_kit.service.database;

import com.msp.openmsp_kit.model.api.tmdb.*;
import com.msp.openmsp_kit.model.mapper.*;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.repository.movie.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseManager {

    private final TMDBMovieRepository tmdbMovieRepository;
    private final TMDBLanguageRepository tmdbLanguageRepository;
    private final TMDBCompanyRepository tmdbCompanyRepository;
    private final TMDBCountryRepository tmdbCountryRepository;
    private final TMDBGenreRepository tmdbGenreRepository;
    private final TMDBMovieMapper tmdbMovieMapper;
    private final TMDBGenreMapper tmdbGenreMapper;
    private final TMDBProductionCompanyMapper tmdbProductionCompanyMapper;
    private final TMDBProductionCountryMapper tmdbProductionCountryMapper;
    private final TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper;

    public DatabaseManager(TMDBMovieRepository tmdbMovieRepository,
            TMDBLanguageRepository tmdbLanguageRepository,
            TMDBCompanyRepository tmdbCompanyRepository,
            TMDBCountryRepository tmdbCountryRepository,
            TMDBGenreRepository tmdbGenreRepository,
            TMDBMovieMapper tmdbMovieMapper,
            TMDBGenreMapper tmdbGenreMapper,
                           TMDBProductionCompanyMapper tmdbProductionCompanyMapper,
                           TMDBProductionCountryMapper tmdbProductionCountryMapper,
                           TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper
                           ) {
        this.tmdbMovieRepository = tmdbMovieRepository;
        this.tmdbLanguageRepository = tmdbLanguageRepository;
        this.tmdbCompanyRepository = tmdbCompanyRepository;
        this.tmdbCountryRepository = tmdbCountryRepository;
        this.tmdbGenreRepository = tmdbGenreRepository;
        this.tmdbMovieMapper = tmdbMovieMapper;
        this.tmdbGenreMapper = tmdbGenreMapper;
        this.tmdbProductionCompanyMapper = tmdbProductionCompanyMapper;
        this.tmdbProductionCountryMapper = tmdbProductionCountryMapper;
        this.tmdbSpokenLanguageMapper = tmdbSpokenLanguageMapper;
    }

    public void saveEntities(List<Result<?>> results) {
        for (Result<?> result : results) {
            saveEntity(result);
        }
    }

    public void saveEntity(Result<?> result) {
        Object entity = result.data();
        if (entity instanceof TMDBMovieDetailsResponse) {
            tmdbMovieRepository.save(tmdbMovieMapper.toEntityFromApi((TMDBMovieDetailsResponse) entity));
        }
        if (entity instanceof TMDBGenreResponse) {
            tmdbGenreRepository.save(tmdbGenreMapper.toEntityFromApi((TMDBGenreResponse) entity));
        }
        if (entity instanceof TMDBCountryResponse) {
            tmdbCountryRepository.save(tmdbProductionCountryMapper.toEntityFromApi((TMDBCountryResponse) entity));
        }
        if (entity instanceof TMDBCompanyDetailsResponse) {
            tmdbCompanyRepository.save(tmdbProductionCompanyMapper.toEntityFromApi((TMDBCompanyDetailsResponse) entity));
        }
        if (entity instanceof TMDBLanguageResponse) {
            tmdbLanguageRepository.save(tmdbSpokenLanguageMapper.toEntityFromApi((TMDBLanguageResponse) entity));
        }
    }
}
