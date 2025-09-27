package com.msp.openmsp_kit.service.downloader;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.EndPoint;
import com.msp.openmsp_kit.model.Resource;
import com.msp.openmsp_kit.model.Source;
import com.msp.openmsp_kit.service.downloader.impl.HttpClientManager;
import com.msp.openmsp_kit.service.downloader.impl.TMDBMovieDetailsDownloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DownloaderRegistry {

    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    Map<DownloaderKey, Downloader> downloaderMap = new HashMap<>();

    DownloaderRegistry(OpenMSPConfig config, JsonParser jsonParser, HttpClientManager httpClientManager) {
        this.config = config;
        this.jsonParser = jsonParser;
        this.httpClientManager = httpClientManager;

        initDownloaders();
    }

    private void initDownloaders() {
        downloaderMap.put(
                new DownloaderKey(Source.TMDB, Resource.MOVIE, EndPoint.DETAILS),
                new TMDBMovieDetailsDownloader(httpClientManager, jsonParser, config)
        );
    }


    public <T, K> Downloader<T, K> getDownloader(Source s, Resource r, EndPoint e) {
        return  downloaderMap.get(new DownloaderKey(s, r, e));
    }
}
