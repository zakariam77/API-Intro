package com.zproject.jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class CreateBugClass {

    private final Logger logger = LogManager.getLogger(CreateBugClass.class);

    @Test
    public void createBug() throws IOException {

        RestAssured.baseURI = "https://zakariamoujahid90.atlassian.net";

        String key = "Basic emFrYXJpYW1vdWphaGlkOTBAZ21haWwuY29tOkFUQVRUM3hGZkdGMFBkcXFXWjN5XzFEQ2MxRV82d0JqSVFjbVVqZm5YeUZTWV9oYk1xUjlNNnE4aWQzZ2VpNHo1dXYtRFJ6aEVPTWJuTzlXM0hUa1BRdHdKYWt1SVRRSXpOYnpyQnNEXzg2bWszbUpGUWM1bHZKVTF6RThNeFpza2lKcGZSOHM2aE5XVTROVUp5NDJjUDN2Ym5Cbmc0cGNvdlVJeDlqaVNNeDhYd0JRQ0VCNTJ0Zz03MDI2RkNEMA==";
        String createBugJson = Files.readString(Path.of(System.getProperty("user.dir") + "/src/test/java/com/zproject/jira/files/createBug.json"), StandardCharsets.UTF_8);


        // create issue
        logger.info("creating issue / post request");
        String response = given()
                .header("Accept", "application/json")
                .header("Authorization", key)
                .header("Content-Type", "application/json")
                .body(createBugJson).when().post("rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201).extract().body().asString();


        // attach file to issue
        logger.info("post request / attach file multipart");
        File screenshotAttach = new File(System.getProperty("user.dir") + "/files/Screenshot.jpg");
        JsonPath js;
        js = new JsonPath(response);
        String issueId = js.getString("id");

        given().log().headers().pathParam("issueId", issueId)
                .header("Content-type", "multipart/form-data")
                .header("X-Atlassian-Token", "no-check")
                .header("Authorization", key)
                .multiPart("file", screenshotAttach)
                .when().post("rest/api/2/issue/{issueId}/attachments")
                .then().log().all().assertThat().statusCode(200);


        //get issue
        logger.info("get request / get issue");
        String getResponse = given().log().all().pathParam("issueId", issueId)
                .header("Accept", "application/json")
                .header("Authorization", key).when().get("/rest/api/2/issue/{issueId}")
                .then().log().all().assertThat().statusCode(200).extract().body().asString();
        System.out.println(getResponse);



        logger.info("assert file has been attached ");
        js = new JsonPath(getResponse);
        String fileName = js.getString("fields.attachment[0].filename");
        Assert.assertEquals(fileName, screenshotAttach.getName());


    }



}
