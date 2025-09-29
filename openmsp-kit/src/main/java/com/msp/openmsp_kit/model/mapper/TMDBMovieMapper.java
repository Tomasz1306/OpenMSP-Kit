package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieDetailsResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBMovieImpl;
import com.msp.openmsp_kit.model.persistence.entity.TMDBMovieEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class TMDBMovieMapper implements ToEntityFromDomainMapper<TMDBMovieEntity, TMDBMovieImpl>,
    ToEntityFromApiMapper<TMDBMovieEntity, TMDBMovieDetailsResponse> {

    private final TMDBGenreMapper genreMapper;
    private final TMDBProductionCompanyMapper productionCompanyMapper;
    private final TMDBProductionCountryMapper productionCountryMapper;
    private final TMDBSpokenLanguageMapper spokenLanguageMapper;

    public TMDBMovieMapper(TMDBGenreMapper genreMapper,
                           TMDBProductionCompanyMapper productionCompanyMapper,
                           TMDBProductionCountryMapper productionCountryMapper,
                           TMDBSpokenLanguageMapper spokenLanguageMapper) {
        this.genreMapper = genreMapper;
        this.productionCompanyMapper = productionCompanyMapper;
        this.productionCountryMapper = productionCountryMapper;
        this.spokenLanguageMapper = spokenLanguageMapper;
    }

    static public TMDBMovieImpl toDomain(TMDBMovieDetailsResponse tmdbMovieDetailsResponse) {
        return TMDBMovieImpl
                .builder()
                .tmdbId(tmdbMovieDetailsResponse.tmdbId())
                .imdbId(tmdbMovieDetailsResponse.imdbId())
                .title(tmdbMovieDetailsResponse.title())
                .overview(tmdbMovieDetailsResponse.overview())
                .releaseDate(tmdbMovieDetailsResponse.releaseDate())
                .backdropPath(tmdbMovieDetailsResponse.backdropPath())
                .posterPath(tmdbMovieDetailsResponse.posterPath())
                .homepage(tmdbMovieDetailsResponse.homepage())
                .budget(tmdbMovieDetailsResponse.budget())
                .language(tmdbMovieDetailsResponse.originalLanguage())
                .popularity(tmdbMovieDetailsResponse.popularity())
                .voteAverage(tmdbMovieDetailsResponse.voteAverage())
                .voteCount(tmdbMovieDetailsResponse.voteCount())
                .genres(tmdbMovieDetailsResponse.genres())
                .productionCountries(tmdbMovieDetailsResponse.productionCountries())
                .productionCompanies(tmdbMovieDetailsResponse.productionCompanies())
                .spokenLanguages(tmdbMovieDetailsResponse.spokenLanguages())
                .adult(tmdbMovieDetailsResponse.adult())
                .video(tmdbMovieDetailsResponse.video())
                .build();
    }

    @Override
    public TMDBMovieEntity toEntityFromDomain(TMDBMovieImpl domain) {
        return TMDBMovieEntity.builder()
                .tmdbId(domain.getTmdbId())
                .imdbId(domain.getImdbId())
                .title(domain.getTitle())
                .overview(domain.getOverview())
                .releaseDate(domain.getReleaseDate())
                .backdropPath(domain.getBackdropPath())
                .posterPath(domain.getPosterPath())
                .homepage(domain.getHomepage())
                .budget(domain.getBudget())
                .language(domain.getLanguage())
                .popularity(domain.getPopularity())
                .voteAverage(domain.getVoteAverage())
                .voteCount(domain.getVoteCount())
//                .genres(new HashSet<>(domain.getGenres().stream().map(genre -> genreMapper.toEntityFromApi(genre)).toList()))
//                .productionCompanies(new HashSet<>(domain.getProductionCompanies().stream().map(TMDBProductionCompanyMapper::toEnity).toList()))
//                .productionCountries(new HashSet<>(domain.getProductionCountries().stream().map(TMDBProductionCountryMapper::toEnity).toList()))
//                .spokenLanguages(new HashSet<>(domain.getSpokenLanguages().stream().map(TMDBSpokenLanguageMapper::toEnity).toList()))
                .build();
    }

    @Override
    public TMDBMovieEntity toEntityFromApi(TMDBMovieDetailsResponse response) {
        return TMDBMovieEntity.builder()
                .tmdbId(response.tmdbId())
                .imdbId(response.imdbId())
                .title(response.title())
                .overview(response.overview())
                .releaseDate(response.releaseDate())
                .backdropPath(response.backdropPath())
                .posterPath(response.posterPath())
                .homepage(response.homepage())
                .budget(response.budget())
                .language(response.originalLanguage())
                .popularity(response.popularity())
                .voteAverage(response.voteAverage())
                .voteCount(response.voteCount())
                .genres(new HashSet<>(response.genres().stream().map(genreMapper::toEntityFromDomain).toList()))
                .productionCompanies(new HashSet<>(response.productionCompanies().stream().map(productionCompanyMapper::toEntityFromDomain).toList()))
                .productionCountries(new HashSet<>(response.productionCountries().stream().map(productionCountryMapper::toEntityFromDomain).toList()))
                .spokenLanguages(new HashSet<>(response.spokenLanguages().stream().map(spokenLanguageMapper::toEntityFromDomain).toList()))
                .build();
    }
}
