package com.msp.openmsp_kit.model.movie;

import java.util.Optional;

public interface MovieAdditionalInfo {
    Optional<String> getHomePage();
    Optional<Integer> getRevenue();
    Optional<Integer> getRuntime();
    Optional<String> getStatus();
    Optional<String> getTagline();
}
