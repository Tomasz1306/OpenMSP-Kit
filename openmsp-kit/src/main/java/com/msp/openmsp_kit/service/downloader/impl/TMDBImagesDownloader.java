package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

@Service
public class TMDBImagesDownloader implements Downloader<byte[], String> {

    private final OpenMSPConfig config;
    private final JsonParser jsonParser;
    private final HttpClientManager httpClientManager;

    public TMDBImagesDownloader(HttpClientManager httpClientManager,
                               JsonParser jsonParser,
                               OpenMSPConfig config) {
        this.httpClientManager = httpClientManager;
        this.jsonParser = jsonParser;
        this.config = config;
    }

    @Override
    public byte[] fetch(String imagePath) {
        try {
            var response = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(imagePath)),
                            HttpResponse.BodyHandlers.ofString())
                    .body();
            return response.getBytes();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URI buildUri(String imagePath) {
        return URI.create(String.format("https://image.tmdb.org/t/p/original/%s", imagePath));
    }
}
