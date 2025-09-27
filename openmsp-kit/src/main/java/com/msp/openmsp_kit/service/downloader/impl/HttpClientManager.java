package com.msp.openmsp_kit.service.downloader.impl;

import org.springframework.stereotype.Component;

import java.net.http.HttpClient;

@Component
public class HttpClientManager {

    HttpClient httpClient;

    public HttpClientManager() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }
}
