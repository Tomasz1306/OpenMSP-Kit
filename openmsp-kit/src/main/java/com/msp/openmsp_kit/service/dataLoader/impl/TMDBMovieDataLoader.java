package com.msp.openmsp_kit.service.dataLoader.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.model.api.tmdb.TMDBMovieId;
import com.msp.openmsp_kit.service.dataLoader.DataLoader;
import com.msp.openmsp_kit.service.parser.JsonParser;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class TMDBMovieDataLoader implements DataLoader<TMDBMovieId> {

    private final ObjectMapper objectMapper;
    private final OpenMSPConfig config;
    private final JsonParser jsonParser;

    TMDBMovieDataLoader(OpenMSPConfig config, JsonParser jsonParser) {
        this.config = config;
        this.jsonParser = jsonParser;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<TMDBMovieId> loadData() {
        try {
            JsonNode jsonNode = objectMapper.readTree(new File(config.getMoviesDataSource()));
            JSONArray jsonArray = new JSONArray(jsonNode.elements().next().toString());
            return jsonArray.toList().stream()
                    .map(jsonData -> {
                        try {
                            return jsonParser.parseJsonToObject(jsonData, TMDBMovieId.class);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
