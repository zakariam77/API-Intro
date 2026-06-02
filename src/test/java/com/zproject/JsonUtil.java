package com.zproject;

import io.restassured.path.json.JsonPath;

public class JsonUtil {


    public static JsonPath stringToJson(String body){
        JsonPath js = new JsonPath(body);
        return js;
    }
}
