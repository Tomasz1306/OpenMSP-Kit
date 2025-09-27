package com.msp.openmsp_kit.model.movie;

import java.util.Optional;

public interface MoviePeopleInfo {
    Optional<String> getDirector();
    Optional<String> getWriter();
    Optional<String> getActors();
}
