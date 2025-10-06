package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieWatchProviderResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieWatchProvidersResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBMovieWatchProvider;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import com.msp.openmsp_kit.util.IsoLanguageCountryMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBMovieWatchProvidersDownloader implements Downloader<List<TMDBMovieWatchProvider>, String> {

    private HttpClientManager httpClientManager;
    private JsonParser jsonParser;
    private OpenMSPConfig openMSPConfig;

    public TMDBMovieWatchProvidersDownloader(HttpClientManager httpClientManager,
                                             JsonParser jsonParser,
                                             OpenMSPConfig openMSPConfig) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.openMSPConfig = openMSPConfig;
    }

    @Override
    public List<TMDBMovieWatchProvider> fetch(String movieId) {
        List<TMDBMovieWatchProvider> providers = new ArrayList<>();
        for (String language : this.openMSPConfig.getLanguages()) {
            providers.addAll(download(movieId, language));
        }
        return providers;
    }

    private List<TMDBMovieWatchProvider> download(String movieId, String language) {
        try {
            String response = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId),
                            openMSPConfig.getTmdbApiKey()
                    ), HttpResponse.BodyHandlers.ofString())
                    .body();
            TMDBMovieWatchProvidersResponse watchProvidersParsed = jsonParser.parseBody(response, TMDBMovieWatchProvidersResponse.class);
            return extractAllProviders(watchProvidersParsed, movieId, language);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private List<TMDBMovieWatchProvider> extractAllProviders(TMDBMovieWatchProvidersResponse watchProvidersParsed, String movieId, String language) {
        List<TMDBMovieWatchProvider> tmdbMovieWatchProviders = new ArrayList<>();
        String country = IsoLanguageCountryMapper.getCountryFromLanguage(language);
        TMDBMovieWatchProviderResponse provider = watchProvidersParsed.results().get(country);
        if (provider == null) {
            return List.of();
        }
        if (provider.ads() != null) {
            for (var ads : provider.ads()) {
                var tmdbMovieWatchProvider = TMDBMovieWatchProvider
                        .builder()
                        .providerId(ads.providerId())
                        .link(provider.link())
                        .type("ads")
                        .movieId(Integer.parseInt(movieId))
                        .iso_639_1(language)
                        .build();
                tmdbMovieWatchProviders.add(tmdbMovieWatchProvider);
            }
        }
        if (provider.buy() != null) {
            for (var buy : provider.buy()) {
                var tmdbMovieWatchProvider = TMDBMovieWatchProvider
                        .builder()
                        .providerId(buy.providerId())
                        .link(provider.link())
                        .type("buy")
                        .movieId(Integer.parseInt(movieId))
                        .iso_639_1(language)
                        .build();
                tmdbMovieWatchProviders.add(tmdbMovieWatchProvider);
            }
        }
        if (provider.rent() != null) {
            for (var rent : provider.rent()) {
                var tmdbMovieWatchProvider = TMDBMovieWatchProvider
                        .builder()
                        .providerId(rent.providerId())
                        .link(provider.link())
                        .type("rent")
                        .movieId(Integer.parseInt(movieId))
                        .iso_639_1(language)
                        .build();
                tmdbMovieWatchProviders.add(tmdbMovieWatchProvider);
            }
        }

        if (provider.flatrate() != null) {
            for (var flatrate : provider.flatrate()) {
                var tmdbMovieWatchProvider = TMDBMovieWatchProvider
                        .builder()
                        .providerId(flatrate.providerId())
                        .link(provider.link())
                        .type("flatrate")
                        .movieId(Integer.parseInt(movieId))
                        .iso_639_1(language)
                        .build();
                tmdbMovieWatchProviders.add(tmdbMovieWatchProvider);
            }
        }
        return tmdbMovieWatchProviders;
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/movie/%s/watch/providers", movieId));
    }
}
