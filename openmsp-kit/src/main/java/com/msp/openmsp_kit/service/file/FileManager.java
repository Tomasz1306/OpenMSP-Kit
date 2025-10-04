package com.msp.openmsp_kit.service.file;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.service.downloader.impl.TMDBImagesDownloader;
import com.msp.openmsp_kit.service.rateLimiter.impl.TMDBFilesRateLimiterImpl;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileManager {

    private final OpenMSPConfig openMSPConfig;
    private final TMDBImagesDownloader tmdbImagesDownloader;
    private final TMDBFilesRateLimiterImpl tmdbFilesRateLimiterImpl;

    public FileManager(OpenMSPConfig openMSPConfig,
                       TMDBImagesDownloader tmdbImagesDownloader,
                       TMDBFilesRateLimiterImpl tmdbFilesRateLimiterImpl) {
        this.openMSPConfig = openMSPConfig;
        this.tmdbImagesDownloader = tmdbImagesDownloader;
        this.tmdbFilesRateLimiterImpl = tmdbFilesRateLimiterImpl;
    }

    public void downloadFile(Result<?> result) {
        tmdbFilesRateLimiterImpl.acquire();
        BufferedImage image = tmdbImagesDownloader.fetch(result.taskId());
        if (image == null) {
            System.err.println("Buffered Image is null");
            return;
        }
        saveFile(image, (TMDBImageResponse) result.data());
    }

    private void saveFile(BufferedImage bufferedImage, TMDBImageResponse imageDomain) {
        try {
            String imageDirectoryPath = openMSPConfig.getImagesDestPath() + "/" + imageDomain.getTmdbId() + "/" + imageDomain.getType() + "/" + imageDomain.getIso_639_1();
            Files.createDirectories(Paths.get(imageDirectoryPath));
            ImageIO.write(bufferedImage,
                    "png",
                    new File(imageDirectoryPath + imageDomain.getFilePath()));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); 
        }
    }
}
