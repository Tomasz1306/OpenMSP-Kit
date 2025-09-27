package com.msp.openmsp_kit.model.movie;

import java.util.Optional;

public interface MovieRatingInfo {
    Optional<Double> getVoteAverage();
    Optional<Integer> getVoteCount();
    Optional<Double> getPopularity();
    Optional<Double> getMetaScore();
    Optional<Double> getImdbVotes();
}
