package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieDetailsResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBMovieImpl;
import com.msp.openmsp_kit.model.mapper.TMDBMovieMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBMovieDetailsDownloader implements Downloader<List<TMDBMovieImpl>, String> {

    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;
    private final TMDBMovieMapper tmdbMovieMapper;

    public TMDBMovieDetailsDownloader(HttpClientManager httpClientManager,
                                      TMDBMovieMapper tmdbMovieMapper,
                                      JsonParser jsonParser,
                                      OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.tmdbMovieMapper = tmdbMovieMapper;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBMovieImpl> fetch(String movieId) {
        List<TMDBMovieDetailsResponse> moviesDetails = new ArrayList<>();
        for (String language : config.getLanguages()) {
            moviesDetails.add(download(movieId, language));
        }
        return moviesDetails.stream().map(tmdbMovieMapper::toDomainFromApi).toList();
    }

    private TMDBMovieDetailsResponse download(String movieId, String language) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId, language), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            return jsonParser.parseBody(jsonBody, TMDBMovieDetailsResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId, String language) {
        return URI.create(String.format("https://api.themoviedb.org/3/movie/%s?language=%s", movieId, language));
    }
}
