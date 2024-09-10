package com.pet.adoption.business.user.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.pet.adoption.business.user.util.ErrorConstants.OBJECT_TO_JSON_EXCEPTION;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonSerializer {

    private final ObjectMapper mapper;

    public String objectToJson(Object obj) {

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Error transforming the object {} to json", obj, e);
            throw OBJECT_TO_JSON_EXCEPTION;
        }
    }

    public <T> T jsonToObject(String json, Class<T> clazz) {

        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Error transforming the json {} to object of class {}", json, clazz, e);
            throw OBJECT_TO_JSON_EXCEPTION;
        }
    }
}
