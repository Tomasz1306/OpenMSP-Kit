package com.msp.openmsp_kit.model.domain.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor

public class OMDBMovieImpl implements Movie, MovieWithBoxOffice, MovieWithAwardsInfo, MovieWithCastAndCrew {

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getReleaseDate() {
        return "";
    }

    @Override
    public String getImdbId() {
        return "";
    }

    @Override
    public String getPosterPath() {
        return "";
    }

    @Override
    public String getAwards() {
        return "";
    }

    @Override
    public String getBoxOffice() {
        return "";
    }

    @Override
    public String getDirector() {
        return "";
    }

    @Override
    public String getWriter() {
        return "";
    }

    @Override
    public String getActors() {
        return "";
    }
}
