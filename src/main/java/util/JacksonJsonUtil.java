package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j

public class JacksonJsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static <T> String getJsonString(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while converting object to string, Object Class : {}", object);
            return "";
        }
    }

    public static <T> String getJsonStringWithoutException(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error occurred while converting object to string, Object Class : {}", object);
            return "";
        }
    }

    public static <T> T getJsonObject(String jsonString, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Error occurred while converting string to object, Class : {}, String : {}", clazz, jsonString);
            return null;
        }
    }

    public static <T> T getGenericJsonObject(String jsonString, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, typeReference);
        } catch (IOException e) {
            log.error("Error occurred while converting string to object, TypeReference : {}, String : {}", typeReference, jsonString);
            return null;
        }
    }
}

