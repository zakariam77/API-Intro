package com.zproject;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public class Jira {

    @Test
    public void createBug() throws IOException {
        String key = "basic emFrYXJpYW1vdWphaGlkOTBAZ21haWwuY29tOkFUQVRUM3hGZkdGMDRQemEzWHh6dFN2OUFiYzdIY1ZybW5TX2J6QWhqTTJQXzliWkVhSGxkQXUyQmxxbUFwYzBiVEpZdFN5cHpteXlZakNuYndNOWhNX3dnQzROYjl2RTYxdWhNdnE3eUdwbXAxb3lIc1ppTG9NSk83Qk95UFNVTVF0NDdIaHZiZ01xcHJwUzNtVndWUTBId1I2TXB2YzVySjYyNjZLTDB3SjZOOGtVbDVDRzVwND03OTIxNTBBRA==";
        String createBugPL = Files.readString(Path.of(System.getProperty("user.dir") + "/src/test/java/com/zproject/files/createBug.json"), StandardCharsets.UTF_8);


        RestAssured.baseURI = "https://zakariamoujahid90.atlassian.net";

        given().log().all().header("Authorization", key)
                .header("Content-Type", "application/json").body(createBugPL).when().post("rest/api/2/issue")
                .then().log().all();


    }

}
