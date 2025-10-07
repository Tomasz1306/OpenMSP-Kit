package com.msp.openmsp_kit.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class OpenMSPConfig {

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Value("${tmdb.movie-ids-source}")
    private String moviesDataSource;

    @Value("${tmdb.images-dest-path}")
    private String imagesDestPath;

    @Value("${tmdb.person-ids-source}")
    private String personDataSource;

    private List<String> languages = List.of("pl", "en");

    {
        System.out.println("Configuration init");
    }
}
