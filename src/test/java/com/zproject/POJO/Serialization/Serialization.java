package com.zproject.POJO.Serialization;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Serialization {

    @Test
    public void addPlace() throws IOException {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace addPlace = new AddPlace();
        Location location =  new Location();
        addPlace.setLocation(new Location(-38.383494, 33.427362));
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setAccuracy(50);
        addPlace.setName("Frontline house");
        addPlace.setPhone_number("(+91) 983 893 3937");
        addPlace.setWebsite("http://google.com");
        addPlace.setLanguage("French-IN");
        addPlace.setTypes(new ArrayList<>(List.of("shoe park","shop")));

        given().log().body().queryParam("key","qaclick123").body(addPlace).when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).log().all();
    }
}
