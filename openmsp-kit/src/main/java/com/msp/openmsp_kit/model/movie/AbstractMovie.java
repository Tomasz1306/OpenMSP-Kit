package com.msp.openmsp_kit.model.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public abstract class AbstractMovie implements Movie {

    @JsonProperty("id")
    protected Integer tmdbId;
    @JsonProperty("imdb_id")
    protected String imdbId;
    @JsonProperty("imdbID")
    protected String imdbID;

    @JsonProperty("title")
    protected String title;
    @JsonProperty("original_title")
    private String originalTitle;
    protected String description;
    protected String releaseDate;
    protected String overview;
    protected String language;

    protected boolean adult;

    protected Integer budget;
    protected Integer boxOffice;
    protected Integer voteCount;
    protected Double voteAverage;
    protected Double popularity;
    protected Double metaScore;
    protected Double imdbVotes;

    public AbstractMovie() {

    }

    public AbstractMovie(AbstractMovieBuilder abstractMovieBuilder) {
        this.tmdbId = abstractMovieBuilder.tmdbId;
        this.imdbId = abstractMovieBuilder.imdbId;
        this.title = abstractMovieBuilder.title;
        this.originalTitle = abstractMovieBuilder.originalTitle;
        this.description = abstractMovieBuilder.description;
        this.releaseDate = abstractMovieBuilder.releaseDate;
        this.overview = abstractMovieBuilder.overview;
        this.language = abstractMovieBuilder.language;
        this.adult = abstractMovieBuilder.adult;
        this.budget = abstractMovieBuilder.budget;
        this.boxOffice = abstractMovieBuilder.boxOffice;
        this.voteCount = abstractMovieBuilder.voteCount;
        this.voteAverage = abstractMovieBuilder.voteAverage;
        this.popularity = abstractMovieBuilder.popularity;
        this.metaScore = abstractMovieBuilder.metaScore;
        this.imdbVotes = abstractMovieBuilder.imdbVotes;
    }

    public abstract static class AbstractMovieBuilder {
        protected Integer tmdbId;
        protected String imdbId;
        protected String imdbID;

        protected String title;
        private String originalTitle;
        protected String description;
        protected String releaseDate;
        protected String overview;
        protected String language;

        protected boolean adult;

        protected Integer budget;
        protected Integer boxOffice;
        protected Integer voteCount;
        protected Double voteAverage;
        protected Double popularity;
        protected Double metaScore;
        protected Double imdbVotes;

        public AbstractMovieBuilder tmdbId(Integer tmdbId) {this.tmdbId = tmdbId; return this; }
        public AbstractMovieBuilder imdbId(String imdbId) {this.imdbId = imdbId; return this; }
        public AbstractMovieBuilder imdbID(String imdbID) {this.imdbID = imdbID; return this; }
        public AbstractMovieBuilder title(String title) {this.title = title; return this; }
        public AbstractMovieBuilder originalTitle(String originalTitle) {this.originalTitle = originalTitle; return this; }
        public AbstractMovieBuilder description(String description) {this.description = description; return this; }
        public AbstractMovieBuilder releaseDate(String releaseDate) {this.releaseDate = releaseDate; return this; }
        public AbstractMovieBuilder overview(String overview) {this.overview = overview; return this; }
        public AbstractMovieBuilder language(String language) {this.language = language; return this; }
        public AbstractMovieBuilder adult(boolean adult) {this.adult = adult; return this; }
        public AbstractMovieBuilder budget(Integer budget) {this.budget = budget; return this; }
        public AbstractMovieBuilder boxOffice(Integer boxOffice) {this.boxOffice = boxOffice; return this; }
        public AbstractMovieBuilder voteCount(Integer voteCount) {this.voteCount = voteCount; return this; }
        public AbstractMovieBuilder voteAverage(Double voteAverage) {this.voteAverage = voteAverage; return this; }
        public AbstractMovieBuilder popularity(Double popularity) {this.popularity = popularity; return this; }
        public AbstractMovieBuilder metaScore(Double metaScore) {this.metaScore = metaScore; return this; }
        public AbstractMovieBuilder imdbVotes(Double imdbVotes) {this.imdbVotes = imdbVotes; return this; }
        public abstract AbstractMovie build();
    }

    public void setTmdbId(Integer tmdbId) {this.tmdbId = tmdbId; }
    public void setImdbId(String imdbId) {this.imdbId = imdbId; }
    public void setImdbID(String imdbID) {this.imdbId = imdbID; } // due to different names in api
    public void setTitle(String title) {this.title = title; }
    public void setOriginalTitle(String originalTitle) {this.originalTitle = originalTitle; }
    public void setDescription(String description) {this.description = description; }
    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate; }
    public void setOverview(String overview) {this.overview = overview; }
    public void setLanguage(String language) {this.language = language; }

    @Override
    public Optional<String> getTitle() {
        return title != null ? Optional.of(title) : Optional.empty();
    }

    @Override
    public Optional<String> getDescription() {
        return description != null ? Optional.of(description) : Optional.empty();
    }

    @Override
    public Optional<String> getReleaseDate() {
        return releaseDate != null ? Optional.of(releaseDate) : Optional.empty();
    }

    @Override
    public Optional<String> getOverview() {
        return overview != null ? Optional.of(overview) : Optional.empty();
    }

    @Override
    public Optional<String> getLanguage() {
        return language != null ? Optional.of(language) : Optional.empty();
    }

    @Override
    public boolean isAdult() {
        return adult;
    }

    @Override
    public Optional<Integer> getBudget() {
        return budget != null ? Optional.of(budget) : Optional.empty();
    }

    @Override
    public Optional<Integer> getBoxOffice() {
        return boxOffice != null ? Optional.of(boxOffice) : Optional.empty();
    }

    @Override
    public Optional<Double> getVoteAverage() {
        return voteAverage != null ? Optional.of(voteAverage) : Optional.empty();
    }

    @Override
    public Optional<Integer> getVoteCount() {
        return voteCount != null ? Optional.of(voteCount) : Optional.empty();
    }

    @Override
    public Optional<Double> getPopularity() {
        return popularity != null ? Optional.of(popularity) : Optional.empty();
    }

    @Override
    public Optional<Double> getMetaScore() {
        return metaScore != null ? Optional.of(metaScore) : Optional.empty();
    }

    @Override
    public Optional<Double> getImdbVotes() {
        return imdbVotes != null ? Optional.of(imdbVotes) : Optional.empty();
    }

    @Override
    public Integer getTmdbId() {
        return tmdbId;
    }

    @Override
    public Optional<String> getOriginalTitle() {
        return originalTitle != null ? Optional.of(originalTitle) : Optional.empty();
    }

    @Override
    public String getImdbId() {
        return imdbId;
    }

    @Override
    public String toString() {
        return
                ", tmdbId=" + tmdbId +
                ", imdbId='" + imdbId + '\'' +
                ", imdbID='" + imdbID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", language='" + language + '\'' +
                ", adult=" + adult +
                ", budget=" + budget +
                ", boxOffice=" + boxOffice +
                ", voteCount=" + voteCount +
                ", voteAverage=" + voteAverage +
                ", popularity=" + popularity +
                ", metaScore=" + metaScore +
                ", imdbVotes=" + imdbVotes;
    }
}
