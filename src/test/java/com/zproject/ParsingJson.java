package com.zproject;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ParsingJson {

    @Test
    public void test() throws IOException {
        String jsonFile = Files.readString(Path.of(System.getProperty("user.dir") + "/src/test/java/com/zproject/files/ParsingJsonPractice.json"), StandardCharsets.UTF_8);
        JsonPath js = new JsonPath(jsonFile);

//1. Print No of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println(count);

//2.Print Purchase Amount

        System.out.println(js.getInt("dashboard.purchaseAmount"));

//3. Print Title of the first course

        System.out.println(js.getString("courses[0].title"));

//4. Print All course titles and their respective Prices

        for (int i = 0; i < count; i++) {
            String title = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses[" + i + "].price");
            System.out.println(title);
            System.out.println(price);
        }
//5. Print no of copies sold by RPA Course

            for(int i=0; i< count; i++){
                if (js.getString("courses[" + i + "].title").equalsIgnoreCase("cypress")) {
                    int copies = js.getInt("courses[" + i + "].copies");
                    System.out.println(copies);
                    break;
                }
            }

            //6. Verify if Sum of all Course prices matches with Purchase Amount
            int sum = 0;

            for(int i=0; i< count; i++){

                sum += js.getInt("courses["+i+"].price") * js.getInt("courses["+i+"].copies");


            }
            int total = js.getInt("dashboard.purchaseAmount");

            Assert.assertEquals(sum, total);
    }
}