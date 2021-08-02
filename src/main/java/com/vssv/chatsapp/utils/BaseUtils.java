package com.vssv.chatsapp.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class BaseUtils {

    public static JSONObject getJsonObject(Object object){
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(String.valueOf(object));
        } catch (ParseException e) {
            return new JSONObject();
        }
    }

    public static String getString(Map<String, Object> map, String key) {
        return String.valueOf(map.get(key));
    }

    public static Map<String, Object> makeResponse(Object status, Object data){
        return makeResponse(status, data, "");
    }

    public static Map<String, Object> makeResponse(Object status, Object data, Object message){
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        response.put("message", message);
        return response;
    }

    public static Map<String, Object> convertJsonToMap(Object json) {
        return JsonUtils.getInstance().convertJsonToMap(json);
    }

    public static String toJsonString(Object object) {
        return JsonUtils.getInstance().toJsonString(object);
    }
}
