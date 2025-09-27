package com.msp.openmsp_kit.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonParser {
    ObjectMapper objectMapper = new ObjectMapper();

    JsonParser() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T parseBody(String body, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(body, clazz);
    }

    public <T> T parseJsonToObject(Object jsonObject, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.convertValue(jsonObject, clazz);
    }
}
