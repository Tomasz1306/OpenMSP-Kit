package com.msp.openmsp_kit.model.movie;

import java.util.Optional;

public interface MovieBasicInfo {
    Optional<String> getTitle();
    Optional<String> getOriginalTitle();
    Optional<String> getDescription();
    Optional<String> getReleaseDate();
    Optional<String> getOverview();
    Optional<String> getLanguage();
    boolean isAdult();
}
