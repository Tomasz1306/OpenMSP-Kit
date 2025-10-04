package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpResponse;

@Service
public class TMDBImagesDownloader implements Downloader<BufferedImage, String> {

    private final HttpClientManager httpClientManager;

    TMDBImagesDownloader(HttpClientManager httpClientManager) {
        this.httpClientManager = httpClientManager;
    }

    @Override
    public BufferedImage fetch(String imagePath) {
        try {
            byte[] response = httpClientManager
                    .getHttpClient()
                    .send(BuildRequest.buildRequest(buildUri(imagePath)), HttpResponse.BodyHandlers.ofByteArray())
                    .body();
            InputStream is = new ByteArrayInputStream(response);
            BufferedImage image = ImageIO.read(is);
            is.close();
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URI buildUri(String imagePath) {
        return URI.create(String.format("https://image.tmdb.org/t/p/w500%s", imagePath));
    }
}
