package com.vssv.chatsapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private ObjectMapper objectMapper;
    public static JsonUtils instance;

    private JsonUtils(){
        objectMapper = new ObjectMapper();
    }

    public static JsonUtils getInstance(){
        if (instance == null){
            instance = new JsonUtils();
        }
        return instance;
    }

    public String toJsonString(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public Map<String, Object> convertJsonToMap(Object json) {
        try {
            return objectMapper.readValue(String.valueOf(json),
                    new TypeReference<Map<String, Object>>(){});
        } catch (Exception exception){
            return new HashMap<>();
        }
    }

    public List<Map<String, Object>> convertJsonToListMap(Object json) {
        try {
            return objectMapper.readValue(String.valueOf(json),
                    new TypeReference<List<Map<String, Object>>>(){});
        } catch (Exception exception){
            return new ArrayList<>();
        }
    }
}
