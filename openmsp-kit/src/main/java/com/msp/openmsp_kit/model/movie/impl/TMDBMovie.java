package com.msp.openmsp_kit.model.movie.impl;

import com.msp.openmsp_kit.model.movie.AbstractMovie;
import com.msp.openmsp_kit.model.movie.MovieAdditionalInfo;

import java.util.List;
import java.util.Optional;

public class TMDBMovie extends AbstractMovie
        implements MovieAdditionalInfo {

    private String homePage;
    private String status;
    private String tagline;
    private Integer revenue;
    private Integer runtime;
    private List<Genre> genres;
    private List<ProductionCompany> productionCompanies;
    private List<ProductionCountry> productionCountry;
    private List<SpokenLanguage> spokenLanguages;

    public TMDBMovie() {
        super();
    }


    TMDBMovie(TMDBMovieBuilder tmdbMovieBuilder) {
        super(tmdbMovieBuilder);
        this.homePage = tmdbMovieBuilder.homePage;
        this.status = tmdbMovieBuilder.status;
        this.tagline = tmdbMovieBuilder.tagline;
        this.revenue = tmdbMovieBuilder.revenue;
        this.runtime = tmdbMovieBuilder.runtime;
        this.genres = tmdbMovieBuilder.genres;
        this.productionCompanies = tmdbMovieBuilder.productionCompanies;
        this.productionCountry = tmdbMovieBuilder.productionCountry;
        this.spokenLanguages = tmdbMovieBuilder.spokenLanguages;
    }

    public static class TMDBMovieBuilder extends AbstractMovieBuilder {
        private String homePage;
        private String status;
        private String tagline;
        private Integer revenue;
        private Integer runtime;
        private List<Genre> genres;
        private List<ProductionCompany> productionCompanies;
        private List<ProductionCountry> productionCountry;
        private List<SpokenLanguage> spokenLanguages;

        public TMDBMovieBuilder() {
            super();
        }

        public TMDBMovieBuilder builder() {
            return new TMDBMovieBuilder();
        }

        public TMDBMovieBuilder homePage(String homePage) { this.homePage = homePage; return this; }
        public TMDBMovieBuilder status(String status) { this.status = status; return this; }
        public TMDBMovieBuilder tagline(String tagline) { this.tagline = tagline; return this; }
        public TMDBMovieBuilder revenue(Integer revenue) { this.revenue = revenue; return this; }
        public TMDBMovieBuilder runtime(Integer runtime) { this.runtime = runtime; return this; }
        public TMDBMovieBuilder genre(List<Genre> genres) { this.genres = genres; return this; }
        public TMDBMovieBuilder productionCompanies(List<ProductionCompany> productionCompanies) {
            this.productionCompanies = productionCompanies;
            return this;
        }
        public TMDBMovieBuilder productionCountry(List<ProductionCountry> productionCountry) {
            this.productionCountry = productionCountry;
            return this;
        }
        public TMDBMovieBuilder spokenLanguage(List<SpokenLanguage> spokenLanguages) {
            this.spokenLanguages = spokenLanguages;
            return this;
        }

        public TMDBMovie build() {
            return new TMDBMovie(this);
        }
    }

    public void setHomePage(String homePage) { this.homePage = homePage; }
    public void setStatus(String status) { this.status = status; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public void setRevenue(Integer revenue) { this.revenue = revenue; }
    public void setRuntime(Integer runtime) { this.runtime = runtime; }
    public void setGenres(List<Genre> genres) { this.genres = genres; }
    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
    public void setProductionCountry(List<ProductionCountry> productionCountry) {
        this.productionCountry = productionCountry;
    }
    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    @Override
    public Optional<String> getHomePage() {
        return homePage != null ? Optional.of(homePage) : Optional.empty();
    }

    @Override
    public Optional<Integer> getRevenue() {
        return revenue != null ? Optional.of(revenue) : Optional.empty();
    }

    @Override
    public Optional<Integer> getRuntime() {
        return runtime != null ? Optional.of(runtime) : Optional.empty();
    }

    @Override
    public Optional<String> getStatus() {
        return status != null ? Optional.of(status) : Optional.empty();
    }

    @Override
    public Optional<String> getTagline() {
        return tagline != null ? Optional.of(tagline) : Optional.empty();
    }

    @Override
    public List<Genre> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "homePage='" + homePage + '\'' +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", genres=" + genres +
                ", productionCompanies=" + productionCompanies +
                ", productionCountry=" + productionCountry +
                ", spokenLanguages=" + spokenLanguages +
                super.toString() +
                '}';
    }
}
