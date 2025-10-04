package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieDetailsResponse;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;

public class TMDBMovieDetailsDownloader implements Downloader<TMDBMovieDetailsResponse, String> {

    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBMovieDetailsDownloader(HttpClientManager httpClientManager,
                                      JsonParser jsonParser,
                                      OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public TMDBMovieDetailsResponse fetch(String movieId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            return jsonParser.parseBody(jsonBody, TMDBMovieDetailsResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/movie/%s?language=pl", movieId));
    }
}
