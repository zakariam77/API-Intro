package com.zproject.jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class CreateBug {

    @Test
    public void createBug() throws IOException {
        String key = "Basic emFrYXJpYW1vdWphaGlkOTBAZ21haWwuY29tOkFUQVRUM3hGZkdGMDRQemEzWHh6dFN2OUFiYzdIY1ZybW5TX2J6QWhqTTJQXzliWkVhSGxkQXUyQmxxbUFwYzBiVEpZdFN5cHpteXlZakNuYndNOWhNX3dnQzROYjl2RTYxdWhNdnE3eUdwbXAxb3lIc1ppTG9NSk83Qk95UFNVTVF0NDdIaHZiZ01xcHJwUzNtVndWUTBId1I2TXB2YzVySjYyNjZLTDB3SjZOOGtVbDVDRzVwND03OTIxNTBBRA==";
        String createBugPL = Files.readString(Path.of(System.getProperty("user.dir") + "/media/Screenshot.jpg"), StandardCharsets.UTF_8);

        System.out.println(createBugPL);
        RestAssured.baseURI = "https://zakariamoujahid90.atlassian.net";

        String response = given().log().all()
                .header("Accept", "application/json")
                .header("Authorization", key)
                .header("Content-Type", "application/json")
                .body(createBugPL).when().post("rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201).extract().body().asString();



        File file = new File("C:/Users/car/Downloads/Screenshot.jpg");
        JsonPath js = new JsonPath(response);
        String issueId = js.getString("id");


        given().log().headers().pathParam("issueId", issueId)
                .header("Content-type", "multipart/form-data")
                .header("X-Atlassian-Token", "no-check")
                .header("Authorization", key)
                .multiPart("file", file)
                .when().post("rest/api/2/issue/{issueId}/attachments")
                .then().log().all().assertThat().statusCode(200);






    }

}
