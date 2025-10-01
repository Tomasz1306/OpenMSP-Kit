package com.msp.openmsp_kit.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class OpenMSPConfig {

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Value("${tmdb.source}")
    private String moviesDataToLoad;

    @Value("${tmdb.images-dest-path}")
    private String imagesDestPath;

    {
        System.out.println("Configuration init");
    }
}
