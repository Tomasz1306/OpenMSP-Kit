package com.msp.openmsp_kit.service.downloader.impl.person;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBPersonDetailsResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBPerson;
import com.msp.openmsp_kit.model.mapper.TMDBPersonMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.downloader.impl.HttpClientManager;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBPersonDetailsDownloader implements Downloader<List<TMDBPerson>, String> {

    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;
    private final TMDBPersonMapper tmdbPersonMapper;

    public TMDBPersonDetailsDownloader(HttpClientManager httpClientManager,
                                       TMDBPersonMapper tmdbPersonMapper,
                                      JsonParser jsonParser,
                                      OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.tmdbPersonMapper = tmdbPersonMapper;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public List<TMDBPerson> fetch(String personId) {
        List<TMDBPerson> personsDetails = new ArrayList<>();
        for (String language : config.getLanguages()) {
            TMDBPerson person = tmdbPersonMapper.toDomainFromApi((download(personId, language)));
            person.setIso_639_1(language);
            personsDetails.add(person);
        }
        return personsDetails;
    }

    private TMDBPersonDetailsResponse download(String personId, String language) {
        try {
            var jsonBody = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(personId, language), config.getTmdbApiKey()),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            return jsonParser.parseBody(jsonBody, TMDBPersonDetailsResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private URI buildUri(String personId, String language) {
        return URI.create(String.format("https://api.themoviedb.org/3/person/%s?language=%s", personId, language));
    }
}
