package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBCountryResponse;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.*;

public class TMDBCountryDownloader implements Downloader<List<TMDBCountryResponse>, String> {
    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBCountryDownloader(HttpClientManager httpClientManager,
                               JsonParser jsonParser,
                               OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBCountryResponse> fetch(String movieId) {
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
            return tmdbCountries;
        } catch (Exception e) {
            throw new  RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/configuration/countries?language=pl"));
    }
}
