package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public final class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtil() {
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    public static <T> String toJson(T entity) throws JsonProcessingException {
        if (entity == null) throw new IllegalArgumentException("entity must not be null");
        return MAPPER.writeValueAsString(entity);
    }

    public static <T> String toJson(List<T> list) throws JsonProcessingException {
        if (list == null) throw new IllegalArgumentException("list must not be null");
        return MAPPER.writeValueAsString(list);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        if (json == null || json.isBlank()) throw new IllegalArgumentException("json must not be blank");
        if (clazz == null) throw new IllegalArgumentException("clazz must not be null");
        return MAPPER.readValue(json, clazz);
    }

    public static <T> List<T> listFromJson(String json, Class<T> elementType) throws JsonProcessingException {
        if (json == null || json.isBlank()) throw new IllegalArgumentException("json must not be blank");
        if (elementType == null) throw new IllegalArgumentException("elementType must not be null");
        CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, elementType);
        return MAPPER.readValue(json, listType);
    }
}