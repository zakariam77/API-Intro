package com.zproject;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class Serialisation {

    public static void main(String[] args) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        FileWriter w = new FileWriter(new File(System.getProperty("user.dir") + "/src/file.json"));


        mapper.writeValue(w,"{\n" +
                "\n" +
                "  \"dashboard\": {\n" +
                "    \"purchaseAmount\": 910,\n" +
                "    \"website\": \"rahulshettyacademy.com\"\n" +
                "  },\n" +
                "\n" +
                "\n" +
                "  \"courses\": [\n" +
                "    {\n" +
                "      \"title\": \"Selenium Python\",\n" +
                "      \"price\": 50,\n" +
                "      \"copies\": 6\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"title\": \"Cypress\",\n" +
                "      \"price\": 40,\n" +
                "      \"copies\": 4\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"title\": \"RPA\",\n" +
                "      \"price\": 45,\n" +
                "      \"copies\": 10\n" +
                "    }\n" +
                "  ]\n" +
                "\n" +
                "}");


    }
}
