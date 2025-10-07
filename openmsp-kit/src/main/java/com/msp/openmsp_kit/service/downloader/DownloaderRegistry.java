package com.msp.openmsp_kit.service.downloader;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.domain.common.EndPoint;
import com.msp.openmsp_kit.model.domain.common.Resource;
import com.msp.openmsp_kit.model.domain.common.Source;
import com.msp.openmsp_kit.model.mapper.*;
import com.msp.openmsp_kit.service.downloader.impl.*;
import com.msp.openmsp_kit.service.downloader.impl.movie.*;
import com.msp.openmsp_kit.service.downloader.impl.person.TMDBPersonDetailsDownloader;
import com.msp.openmsp_kit.service.downloader.impl.person.TMDBPersonImagesDownload;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DownloaderRegistry {

    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    private final TMDBMovieMapper tmdbMovieMapper;
    private final TMDBGenreMapper tmdbGenreMapper;
    private final TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper;
    private final TMDBProductionCountryMapper tmdbProductionCountryMapper;
    private final TMDBWatchProviderMapper tmdbWatchProviderMapper;
    private final TMDBVideoMapper tmdbVideoMapper;
    private final TMDBMovieImageMapper tmdbMovieImageMapper;
    private final TMDBPersonImageMapper tmdbPersonImageMapper;

    private final TMDBPersonMapper tmdbPersonMapper;

    Map<DownloaderKey, Downloader> downloaderMap = new HashMap<>();

    DownloaderRegistry(OpenMSPConfig config,
                       JsonParser jsonParser,
                       HttpClientManager httpClientManager,
                       TMDBMovieMapper tmdbMovieMapper,
                       TMDBGenreMapper tmdbGenreMapper,
                       TMDBSpokenLanguageMapper tmdbSpokenLanguageMapper,
                       TMDBProductionCountryMapper tmdbProductionCountryMapper,
                       TMDBWatchProviderMapper tmdbWatchProviderMapper,
                       TMDBVideoMapper tmdbVideoMapper,
                       TMDBPersonMapper tmdbPersonMapper,
                       TMDBMovieImageMapper tmdbMovieImageMapper,
                       TMDBPersonImageMapper tmdbPersonImageMapper) {
        this.config = config;
        this.jsonParser = jsonParser;
        this.httpClientManager = httpClientManager;
        this.tmdbMovieMapper = tmdbMovieMapper;
        this.tmdbGenreMapper = tmdbGenreMapper;
        this.tmdbSpokenLanguageMapper = tmdbSpokenLanguageMapper;
        this.tmdbProductionCountryMapper = tmdbProductionCountryMapper;
        this.tmdbWatchProviderMapper = tmdbWatchProviderMapper;
        this.tmdbVideoMapper = tmdbVideoMapper;
        this.tmdbPersonMapper = tmdbPersonMapper;
        this.tmdbMovieImageMapper = tmdbMovieImageMapper;
        this.tmdbPersonImageMapper = tmdbPersonImageMapper;

        initDownloaders();
    }

    private void initDownloaders() {
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.MOVIE, EndPoint.DETAILS),
                new TMDBMovieDetailsDownloader(httpClientManager, tmdbMovieMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.GENRES, EndPoint.CONFIG),
                new TMDBGenreDownloader(httpClientManager, tmdbGenreMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.COUNTRIES, EndPoint.CONFIG),
                new TMDBCountryDownloader(httpClientManager, tmdbProductionCountryMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.COMPANIES, EndPoint.DETAILS),
                new TMDBCompanyDownloader(httpClientManager, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.LANGUAGES, EndPoint.CONFIG),
                new TMDBLanguageDownloader(httpClientManager, tmdbSpokenLanguageMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.WATCH_PROVIDERS, EndPoint.CONFIG),
                new TMDBMovieWatchProviderListDownloader(httpClientManager, tmdbWatchProviderMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.MOVIE, EndPoint.IMAGES),
                new TMDBMovieImagesDownloader(httpClientManager, tmdbMovieImageMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.PEOPLE, EndPoint.IMAGES),
                new TMDBPersonImagesDownload(httpClientManager, tmdbPersonImageMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.MOVIE, EndPoint.WATCH_PROVIDERS),
                new TMDBMovieWatchProvidersDownloader(httpClientManager, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.MOVIE, EndPoint.VIDEOS),
                new TMDBMovieVideoDownloader(httpClientManager, tmdbVideoMapper, jsonParser, config)
        );
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.PEOPLE, EndPoint.DETAILS),
                new TMDBPersonDetailsDownloader(httpClientManager, tmdbPersonMapper, jsonParser, config)
        );
    }

    public <T, K> Downloader<T, K> getDownloader(Source s, Resource r, EndPoint e) {
        return  downloaderMap.get(new DownloaderKey(s, r, e));
    }
}
