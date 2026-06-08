package com.zproject.jira;

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



        String key = "Basic emFrYXJpYW1vdWphaGlkOTBAZ21haWwuY29tOkFUQVRUM3hGZkdGMDRQemEzWHh6dFN2OUFiYzdIY1ZybW5TX2J6QWhqTTJQXzliWkVhSGxkQXUyQmxxbUFwYzBiVEpZdFN5cHpteXlZakNuYndNOWhNX3dnQzROYjl2RTYxdWhNdnE3eUdwbXAxb3lIc1ppTG9NSk83Qk95UFNVTVF0NDdIaHZiZ01xcHJwUzNtVndWUTBId1I2TXB2YzVySjYyNjZLTDB3SjZOOGtVbDVDRzVwND03OTIxNTBBRA==";
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
