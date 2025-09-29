package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBCompanyDetailsResponse;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;

public class TMDBCompanyDownloader implements Downloader<TMDBCompanyDetailsResponse, String> {
    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBCompanyDownloader(HttpClientManager httpClientManager,
                                 JsonParser jsonParser,
                                 OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public TMDBCompanyDetailsResponse fetch(String companyId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(companyId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            return jsonParser.parseBody(jsonBody, TMDBCompanyDetailsResponse.class);
        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String companyId) {
        return URI.create(String.format("https://api.themoviedb.org/3/company/%s?language=pl", companyId));
    }
}
