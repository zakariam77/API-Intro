package com.zproject.POJO.deserialization;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class POJOPractice {

    public static void main(String[] args){

      String response =  given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                        .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                        .formParam("grant_type", "client_credentials")
                        .formParam("scope", "trust")
                        .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                        .asString();


        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");
        System.out.println(accessToken);

        GetCourse gc = given().queryParam("access_token", accessToken).when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then().log().all().extract().as(GetCourse.class);

        List<Api> apiCourses = gc.getCourses().getApi();
        // printing course price based on condition
       for(int i = 0; i < apiCourses.size(); i++){

           if(apiCourses.get(i).getCourseTitle().contains("Webservices")){
               System.out.println(apiCourses.get(i).getPrice());
           }
       }
       // asserting course titles
       String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};

       List<String> courseTitlesL = new ArrayList<>();

       for(int i= 0; i < gc.getCourses().getWebAutomation().size(); i++){
        courseTitlesL.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
           }

        List<String> expectedList = Arrays.asList(courseTitles);

       Assert.assertEquals(courseTitlesL, expectedList);


    }
    }

