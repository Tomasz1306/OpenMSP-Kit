package com.msp.openmsp_kit.service.downloader.impl.movie;

import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieVideosResponse;
import com.msp.openmsp_kit.model.domain.tmdb.TMDBMovieVideo;
import com.msp.openmsp_kit.model.mapper.TMDBVideoMapper;
import com.msp.openmsp_kit.service.downloader.BuildRequest;
import com.msp.openmsp_kit.service.downloader.Downloader;
import com.msp.openmsp_kit.service.downloader.impl.HttpClientManager;
import com.msp.openmsp_kit.service.parser.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class TMDBMovieVideoDownloader implements Downloader<List<TMDBMovieVideo>, String> {

    private final HttpClientManager httpClientManager;
    private final TMDBVideoMapper videoMapper;
    private final JsonParser jsonParser;
    private final OpenMSPConfig openMSPConfig;

    public TMDBMovieVideoDownloader(HttpClientManager httpClientManager,
                                    TMDBVideoMapper videoMapper,
                                    JsonParser jsonParser,
                                    OpenMSPConfig openMSPConfig) {
        this.httpClientManager = httpClientManager;
        this.videoMapper = videoMapper;
        this.jsonParser = jsonParser;
        this.openMSPConfig = openMSPConfig;
    }

    @Override
    public List<TMDBMovieVideo> fetch(String movieId) {
        List<TMDBMovieVideo> videos = new ArrayList<>();
        for (String language : openMSPConfig.getLanguages()) {
            videos.addAll(download(movieId, language));
        }
        return videos;
    }

    private List<TMDBMovieVideo> download(String movieId, String language) {
        try {
            String response = httpClientManager.getHttpClient().send(
                    BuildRequest.buildRequest(buildUri(movieId, language), openMSPConfig.getTmdbApiKey()),
                    HttpResponse.BodyHandlers.ofString()
            ).body();
            TMDBMovieVideosResponse videos = jsonParser.parseBody(response, TMDBMovieVideosResponse.class);
            return videos
                    .results()
                    .stream()
                    .map(videoMapper::toDomainFromApi)
                    .toList()
                    .stream()
                    .peek(video -> video.setTmdbMovieId(Integer.parseInt(movieId)))
                    .toList();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URI buildUri(String movieId, String language) {
        return URI.create(String.format("https://api.themoviedb.org/3/movie/%s/videos?language=%s", movieId, language));
    }
}
