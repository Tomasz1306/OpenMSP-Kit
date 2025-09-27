package com.msp.openmsp_kit.model.movie;

import java.util.Optional;

public interface MovieImagesInfo {
    Optional<String> getBackdropPath();
    Optional<String> getPosterPath();
}
