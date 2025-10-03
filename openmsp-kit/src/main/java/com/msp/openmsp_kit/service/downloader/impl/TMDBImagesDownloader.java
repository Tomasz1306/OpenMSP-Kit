package com.msp.openmsp_kit.service.downloader.impl;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Service
public class TMDBImagesDownloader implements Downloader<BufferedImage, String> {

    @Override
    public BufferedImage fetch(String imagePath) {
        try {
            return ImageIO.read(new URL(buildUri(imagePath).toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URI buildUri(String imagePath) {
        return URI.create(String.format("https://image.tmdb.org/t/p/original%s", imagePath));
    }
}
