package com.zproject;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Auth2 {

    @Test
    public void getTokenGetCourses(){
        String response1 =
        given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        JsonPath js = new JsonPath(response1);
        String accessToken = js.getString("access_token");

        given().queryParam("access_token", accessToken).when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then().log().all();


    }
}
