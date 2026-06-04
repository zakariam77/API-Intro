package com.zproject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Intro {
    private final static Logger logger = LogManager.getLogger(Intro.class);
    @Test
    public void test() throws IOException {

        String requestBody = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "/src/test/java/com/zproject/files/request.json"), StandardCharsets.UTF_8);

        // given -- all input details
        // when -- submit the api (resource / http methods )
        // then -- validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";


//add address and get place_id
        logger.info("post request / extracting place_id");
        String response =  given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
        .body(requestBody).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(response);
       String place_id = js1.getString("place_id");

        System.out.println(place_id);
        logger.info("place_id extracted");

//update address
        String newAddress = "mooodj";
        String updateBody = "{\n" +
                "    \"place_id\": \"" + place_id + "\",\n" +
                "    \"address\": \""+ newAddress +"\",\n" +
                "    \"key\" : \"qaclick123\"\n" +
                "}";
        given().log().all().queryParam("key","qaclick123").header("Content_type", "application/json")
                .body(updateBody).when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

//get address
         String response2 = given().log().all().queryParam("place_id", place_id)
                .queryParam("key", "qaclick123").when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
         JsonPath js = JsonUtil.stringToJson(response2);
        String address = js.getString("address");

        Assert.assertEquals(address, newAddress);




} }
