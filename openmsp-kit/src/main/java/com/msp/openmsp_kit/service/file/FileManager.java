package com.msp.openmsp_kit.service.file;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.domain.movie.TMDBImage;
import com.msp.openmsp_kit.model.result.Result;
import com.msp.openmsp_kit.service.downloader.impl.TMDBImagesDownloader;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Service
public class FileManager {

    private final OpenMSPConfig openMSPConfig;
    private final TMDBImagesDownloader tmdbImagesDownloader;

    public FileManager(OpenMSPConfig openMSPConfig,
                       TMDBImagesDownloader tmdbImagesDownloader) {
        this.openMSPConfig = openMSPConfig;
        this.tmdbImagesDownloader = tmdbImagesDownloader;
    }

    public void downloadFile(Result<?> result) {
        byte[] image = tmdbImagesDownloader.fetch(result.taskId());
        saveFile(image, (TMDBImage) result.data());
    }

    private void saveFile(byte[] imageData, TMDBImage imageDomain) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
            ImageIO.write(bufferedImage,
                    "png",
                    new File(openMSPConfig.getImagesDestPath() + "/" + imageDomain.getId() + "/" + imageDomain.getIso_639_1() + "/" + imageDomain.getFile_path() + ".png"));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); 
        }
    }
}
