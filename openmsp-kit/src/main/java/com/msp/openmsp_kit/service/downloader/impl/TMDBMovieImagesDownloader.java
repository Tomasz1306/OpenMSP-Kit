package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieImagesResponse;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBMovieImagesDownloader implements Downloader<List<TMDBImageResponse>, String> {
    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBMovieImagesDownloader(HttpClientManager httpClientManager,
                                     JsonParser jsonParser,
                                     OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBImageResponse> fetch(String movieId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            TMDBMovieImagesResponse response = jsonParser.parseBody(jsonBody, TMDBMovieImagesResponse.class);
            List<TMDBImageResponse> tmdbImages = new ArrayList<>();
            tmdbImages.addAll(response.backDrops().stream().peek(image ->
                {image.setType("backdrop"); image.setTmdbId(Integer.parseInt(movieId));}).toList());
            tmdbImages.addAll(response.logos().stream().peek(image ->
                {image.setType("logo"); image.setTmdbId(Integer.parseInt(movieId));}).toList());
            tmdbImages.addAll(response.posters().stream().peek(image ->
                {image.setType("poster"); image.setTmdbId(Integer.parseInt(movieId));}).toList());
            return tmdbImages;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/movie/%s/images?language=pl", movieId));
    }
}

