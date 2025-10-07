package com.msp.openmsp_kit.service.file;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBImageResponse;
import com.msp.openmsp_kit.model.domain.result.Result;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBMovieImage;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBPersonImage;
import com.msp.openmsp_kit.service.downloader.impl.TMDBImagesDownloader;
import com.msp.openmsp_kit.service.metrics.MetricsCollector;
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
    private final MetricsCollector metricsCollector;

    public FileManager(OpenMSPConfig openMSPConfig,
                       TMDBImagesDownloader tmdbImagesDownloader,
                       TMDBFilesRateLimiterImpl tmdbFilesRateLimiterImpl,
                       MetricsCollector metricsCollector) {
        this.openMSPConfig = openMSPConfig;
        this.tmdbImagesDownloader = tmdbImagesDownloader;
        this.tmdbFilesRateLimiterImpl = tmdbFilesRateLimiterImpl;
        this.metricsCollector = metricsCollector;
    }

    public void downloadFile(Result<?> result) {
        tmdbFilesRateLimiterImpl.acquire();
        BufferedImage image = tmdbImagesDownloader.fetch(result.taskId());
        if (image == null) {
            System.err.println("Buffered Image is null");
            return;
        }
        if (result.data() instanceof TMDBMovieImage) {
            TMDBMovieImage tmdbMovieImage = (TMDBMovieImage) result.data();
            String directoryPath = openMSPConfig.getImagesDestPath() + "/movies/" +
                    tmdbMovieImage.getTmdbId() + "/" +
                    tmdbMovieImage.getType() + "/" +
                    tmdbMovieImage.getIso6391();
            String fileName = tmdbMovieImage.getFilePath();
            saveFile(image, directoryPath, fileName);
            metricsCollector.incrementTotalFileSaves();
        } else if (result.data() instanceof TMDBPersonImage) {
            TMDBPersonImage tmdbPersonImage = (TMDBPersonImage) result.data();
            String directoryPath = openMSPConfig.getImagesDestPath() + "/people/" +
                    tmdbPersonImage.getPersonId();
            String fileName = tmdbPersonImage.getFilePath();
            saveFile(image, directoryPath, fileName);
            metricsCollector.incrementTotalFileSaves();
        }
    }

    private void saveFile(BufferedImage bufferedImage, String directoryPath, String fileName) {
        try {
            Files.createDirectories(Paths.get(directoryPath));
            ImageIO.write(bufferedImage,
                    "png",
                    new File(directoryPath + fileName));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); 
        }
    }
}
