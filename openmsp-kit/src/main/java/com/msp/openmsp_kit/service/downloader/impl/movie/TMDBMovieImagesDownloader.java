package com.msp.openmsp_kit.service.downloader.impl.movie;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieImagesResponse;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.downloader.impl.HttpClientManager;
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
        List<TMDBImageResponse> images = new ArrayList<>();
        for (String language : config.getLanguages()) {
            images.addAll(download(movieId, language));
        }
        return images;
    }

    private List<TMDBImageResponse> download(String movieId, String language) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId, language), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            TMDBMovieImagesResponse response = jsonParser.parseBody(jsonBody, TMDBMovieImagesResponse.class);
            List<TMDBImageResponse> tmdbImages = new ArrayList<>();
            if (response.backDrops() != null) {
                tmdbImages.addAll(response.backDrops().subList(0, Math.min(response.backDrops().size(), 20)).stream().peek(image ->
                {image.setType("backdrop"); image.setTmdbId(Integer.parseInt(movieId));}).toList());
            }
            if (response.logos() != null) {
                tmdbImages.addAll(response.logos().subList(0, Math.min(response.logos().size(), 5)).stream().peek(image ->
                {image.setType("logo"); image.setTmdbId(Integer.parseInt(movieId));}).toList());
            }
            if (response.posters() != null) {
                tmdbImages.addAll(response.posters().subList(0, Math.min(response.posters().size(), 5)).stream().peek(image ->
                {image.setType("poster"); image.setTmdbId(Integer.parseInt(movieId));}).toList());
            }
            return tmdbImages;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId, String language) {
        return URI.create(String.format("https://api.themoviedb.org/3/movie/%s/images?language=%s", movieId, language));
    }
}

