package com.msp.openmsp_kit.model.mapper;

import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieDetailsResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBMovie;
import com.msp.openmsp_kit.model.persistence.entity.movie.TMDBMovieEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class TMDBMovieMapper implements ToEntityFromDomainMapper<TMDBMovieEntity, TMDBMovie>,
    ToEntityFromApiMapper<TMDBMovieEntity, TMDBMovieDetailsResponse>, ToDomainFromApiMapper<TMDBMovie, TMDBMovieDetailsResponse> {

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

    @Override
    public TMDBMovieEntity toEntityFromDomain(TMDBMovie domain) {
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
                .iso6391(domain.getIso_639_1())
                .genres(new HashSet<>(domain.getGenres().stream().map(genreMapper::toEntityFromDomain).toList()))
//                .productionCompanies(new HashSet<>(domain.getProductionCompanies().stream().map(TMDBProductionCompanyMapper::toEnity).toList()))
//                .productionCountries(new HashSet<>(domain.getProductionCountries().stream().map(productionCountryMapper::toEntityFromDomain).toList()))
//                .spokenLanguages(new HashSet<>(domain.getSpokenLanguages().stream().map(spokenLanguageMapper::toEntityFromDomain).toList()))
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
                .genres(new HashSet<>(response.genres().stream().map(genreMapper::toEntityFromApi).toList()))
//                .productionCompanies(new HashSet<>(response.productionCompanies().stream().map(productionCompanyMapper::toEntityFromDomain).toList()))
                .productionCountries(new HashSet<>(response.productionCountries().stream().map(productionCountryMapper::toEntityFromApi).toList()))
                .spokenLanguages(new HashSet<>(response.spokenLanguages().stream().map(spokenLanguageMapper::toEntityFromApi).toList()))
                .build();
    }

    @Override
    public TMDBMovie toDomainFromApi(TMDBMovieDetailsResponse response) {
        return TMDBMovie
                .builder()
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
                .genres(response.genres().stream().map(genreMapper::toDomainFromApi).toList())
                .productionCountries(response.productionCountries().stream().map(productionCountryMapper::toDomainFromApi).toList())
//                .productionCompanies(response.productionCompanies())
                .spokenLanguages(response.spokenLanguages().stream().map(spokenLanguageMapper::toDomainFromApi).toList())
                .adult(response.adult())
                .video(response.video())
                .build();
    }
}
