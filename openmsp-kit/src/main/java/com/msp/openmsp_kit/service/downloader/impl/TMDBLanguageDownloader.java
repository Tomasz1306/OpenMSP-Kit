package com.msp.openmsp_kit.service.downloader.impl;

import aj.org.objectweb.asm.TypeReference;
import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBCountryResponse;
import com.msp.openmsp_kit.model.api.tmdb.TMDBLanguageResponse;
import com.msp.openmsp_kit.model.domain.movie.TMDBSpokenLanguage;
import com.msp.openmsp_kit.model.mapper.TMDBSpokenLanguageMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBLanguageDownloader implements Downloader<List<TMDBSpokenLanguage>, String> {
    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;
    private final TMDBSpokenLanguageMapper spokenLanguageMapper;

    public TMDBLanguageDownloader(HttpClientManager httpClientManager,
                                 TMDBSpokenLanguageMapper spokenLanguageMapper,
                                 JsonParser jsonParser,
                                 OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.spokenLanguageMapper = spokenLanguageMapper;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBSpokenLanguage> fetch(String movieId) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(movieId), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            JSONArray jsonArray = new JSONArray(jsonBody);
            List<TMDBLanguageResponse> tmdbLanguages = new ArrayList<>();
            for (Object object: jsonArray) {
                TMDBLanguageResponse newResponse = new TMDBLanguageResponse(
                        (String) ((JSONObject)object).get("iso_639_1"),
                        (String) ((JSONObject)object).get("english_name"),
                        (String) ((JSONObject)object).get("name")
                );
                tmdbLanguages.add(newResponse);
            }
            return tmdbLanguages.stream().map(spokenLanguageMapper::toDomainFromApi).toList();
        } catch (Exception e) {
            throw new   RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String movieId) {
        return URI.create(String.format("https://api.themoviedb.org/3/configuration/languages"));
    }
}
