package com.msp.openmsp_kit.service.downloader;

import java.net.URI;
import java.net.http.HttpRequest;

public class BuildRequest {
    public static HttpRequest buildRequest(URI uri, String authToken) {
        return HttpRequest
                .newBuilder()
                .GET()
                .uri(uri)
                .header("Authorization", "Bearer " + authToken)
                .build();
    }

    public static HttpRequest buildRequest(URI uri) {
        return HttpRequest
                .newBuilder()
                .GET()
                .uri(uri)
                .build();
    }
}
