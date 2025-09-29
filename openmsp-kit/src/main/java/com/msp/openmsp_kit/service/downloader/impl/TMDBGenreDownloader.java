package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBGenreResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBGenresResponse;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;

public class TMDBGenreDownloader implements Downloader<List<TMDBGenreResponse>, String> {
    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBGenreDownloader(HttpClientManager httpClientManager,
                                      JsonParser jsonParser,
                                      OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBGenreResponse> fetch(String movieId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            TMDBGenresResponse response = jsonParser.parseBody(jsonBody, TMDBGenresResponse.class);
            return response
                    .genres()
                    .stream()
                    .map(genre -> new TMDBGenreResponse(genre.id(), genre.name())).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/genre/movie/list?language=pl"));
    }
}
