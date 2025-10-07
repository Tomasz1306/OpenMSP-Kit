package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBCountryResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBProductionCountry;
import com.msp.openmsp_kit.model.mapper.TMDBProductionCountryMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.*;

public class TMDBCountryDownloader implements Downloader<List<TMDBProductionCountry>, String> {
    private final OpenMSPConfig config;
    private final HttpClientManager httpClientManager;
    private final JsonParser jsonParser;
    private final TMDBProductionCountryMapper productionCountryMapper;

    public TMDBCountryDownloader(HttpClientManager httpClientManager,
                               TMDBProductionCountryMapper productionCountryMapper,
                               JsonParser jsonParser,
                               OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.productionCountryMapper = productionCountryMapper;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBProductionCountry> fetch(String movieId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();

            JSONArray jsonArray = new JSONArray(jsonBody);
            List<TMDBCountryResponse> tmdbCountries = new ArrayList<>();
            for (Object object: jsonArray) {
                TMDBCountryResponse newResponse = new TMDBCountryResponse(
                        (String) ((JSONObject)object).get("iso_3166_1"),
                        (String) ((JSONObject)object).get("english_name"),
                        (String) ((JSONObject)object).get("native_name")
                );
                tmdbCountries.add(newResponse);
            }
            return tmdbCountries.stream().map(productionCountryMapper::toDomainFromApi).toList();
        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/configuration/countries?language=pl"));
    }
}
