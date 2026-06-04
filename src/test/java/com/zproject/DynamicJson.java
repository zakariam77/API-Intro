package com.zproject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class DynamicJson {


    @Test(dataProvider = "sqlData")
    public void dynamicPayload(String isbn, String aisle) {

        RestAssured.baseURI = "http://216.10.245.166";
        // add book
        String response = given().header("ContentType", "application/json").
                body(rawData(isbn, aisle)).when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = new JsonPath(response);
        String bookID = js.get("ID");

        // delete book

        given().queryParam("ContentType", "application/json").body("{\"ID\" : \""+bookID+"\"}")
                .when().post("Library/DeleteBook.php").then().log().all();

    }






    @DataProvider(name= "sqlData", parallel = true)
    public Iterator<String[]> sqlData() throws SQLException {

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select isbn, aisle from jsondata");
        List<String[]> list = new ArrayList<>();
        while(rs.next()){
           String isbn = rs.getString("isbn");
           String aisle = rs.getString("aisle");
           list.add(new String[] {isbn, aisle});
        }
        conn.close();
        statement.close();
        rs.close();
        return list.iterator();
    }

    public String rawData(String isbn, String aisle){
        String request = "{\n" +
                "\"name\":\"Learn Appium Automation withd Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"Johnc foe\"\n" +
                "}";
        return request;
    }


}
