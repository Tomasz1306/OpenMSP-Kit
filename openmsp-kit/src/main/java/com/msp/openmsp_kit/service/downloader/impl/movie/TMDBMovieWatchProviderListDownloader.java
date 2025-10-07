package com.msp.openmsp_kit.service.downloader.impl.movie;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieWatchProviderListResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBWatchProviderResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBWatchProvider;
import com.msp.openmsp_kit.model.mapper.TMDBWatchProviderMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.downloader.impl.HttpClientManager;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBMovieWatchProviderListDownloader implements Downloader<List<TMDBWatchProvider>,String> {
    private HttpClientManager httpClientManager;
    private JsonParser jsonParser;
    private OpenMSPConfig openMSPConfig;
    private TMDBWatchProviderMapper tmdbWatchProviderMapper;

    public TMDBMovieWatchProviderListDownloader(HttpClientManager httpClientManager,
                                             TMDBWatchProviderMapper tmdbWatchProviderMapper,
                                             JsonParser jsonParser,
                                             OpenMSPConfig openMSPConfig) {
        this.httpClientManager = httpClientManager;
        this.tmdbWatchProviderMapper = tmdbWatchProviderMapper;
        this.jsonParser = jsonParser;
        this.openMSPConfig = openMSPConfig;
    }

    @Override
    public List<TMDBWatchProvider> fetch(String watch_region) {
        List<TMDBWatchProvider> tmdbWatchProviders = new ArrayList<>();
        tmdbWatchProviders.addAll(download("en"));
        return tmdbWatchProviders;
    }

    private List<TMDBWatchProvider> download(String language) {
        try {
            String response = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(language),
                            openMSPConfig.getTmdbApiKey()
                    ), HttpResponse.BodyHandlers.ofString())
                    .body();
            TMDBMovieWatchProviderListResponse parsedWatchProviders = jsonParser.parseBody(response, TMDBMovieWatchProviderListResponse.class);
            List<TMDBWatchProviderResponse> watchProviderResponses = parsedWatchProviders.results();
            return watchProviderResponses.stream().map(tmdbWatchProviderMapper::toDomainFromApi).toList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String language) {
        return URI.create(String.format("https://api.themoviedb.org/3/watch/providers/movie?language=%s", language));
    }
}
