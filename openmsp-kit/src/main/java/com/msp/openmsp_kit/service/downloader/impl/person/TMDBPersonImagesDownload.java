package com.msp.openmsp_kit.service.downloader.impl.person;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBPersonImagesResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBImage;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBPersonImage;
import com.msp.openmsp_kit.model.mapper.TMDBPersonImageMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.downloader.impl.HttpClientManager;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBPersonImagesDownload implements Downloader<List<TMDBPersonImage>, String> {
    private final OpenMSPConfig config;
    private final TMDBPersonImageMapper tmdbPersonImageMapper;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBPersonImagesDownload(HttpClientManager httpClientManager,
                                     TMDBPersonImageMapper tmdbPersonImageMapper,
                                     JsonParser jsonParser,
                                     OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.tmdbPersonImageMapper = tmdbPersonImageMapper;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBPersonImage> fetch(String movieId) {
        return download(movieId);
    }

    private List<TMDBPersonImage> download(String movieId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            TMDBPersonImagesResponse response = jsonParser.parseBody(jsonBody, TMDBPersonImagesResponse.class);
            if (response.profiles() == null) {
                return List.of();
            }
            return new ArrayList<>(
                    response.profiles()
                            .stream()
                            .map(tmdbPersonImageMapper::toDomainFromApi)
                            .peek(image -> {image.setPersonId(Integer.parseInt(movieId));})
                            .toList()
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/person/%s/images", movieId));
    }
}
