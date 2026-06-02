package com.zproject;

public class Data {


    public static String getBook(String isbn, String aisle){

         String request = "{\n" +
                "\"name\":\"Learn Appium Automation withd Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"Johnc foe\"\n" +
                "}";
        return request;
    }
}
