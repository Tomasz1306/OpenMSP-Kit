package com.msp.openmsp_kit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenMSPConfig {

    @Value("${tmdb.api-key}")
    private String tmdbApiKey;

    @Value("${tmdb.source}")
    private String moviesDataToLoad ;

    {
        System.out.println("Configuration init");
    }

    public String getTmdbApiKey() {
        return tmdbApiKey;
    }
    public String getMoviesDataToLoad() {return moviesDataToLoad;}
}
