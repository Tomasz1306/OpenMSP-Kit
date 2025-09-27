package com.msp.openmsp_kit.service.dataLoader.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msp.openmsp_kit.config.OpenMSPConfig;
import com.msp.openmsp_kit.service.dataLoader.DataLoader;
import org.json.JSONArray;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class TMDBMovieDataLoader implements DataLoader {

    private final ObjectMapper objectMapper;
    private final OpenMSPConfig config;

    TMDBMovieDataLoader(OpenMSPConfig config) {
        this.config = config;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<Object> loadData() {
        try {
            JsonNode jsonNode = objectMapper.readTree(new File(config.getMoviesDataToLoad()));
            JSONArray jsonArray = new JSONArray(jsonNode.elements().next().toString());
            return jsonArray.toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }
}
