package com.bambr.finabay_homework.errors;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by Daniil on 10.09.2015.
 */
public class ErrorMessageCreator {
    private static Gson serializer = new Gson();

    public static JsonObject createErrorJson(Map<String, String> errorData) {
        JsonObject errorJsonData = new JsonObject();
        errorData.entrySet().forEach(entry -> errorJsonData.addProperty(entry.getKey(), entry.getValue()));
        JsonObject result = new JsonObject();
        result.add("error", errorJsonData);
        return result;
    }
}
