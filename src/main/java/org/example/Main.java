package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        //LECTURA DEL ARCHIVO JSON
        //https://mkyong.com/java/json-simple-example-read-and-write-json/
        JSONParser parser = new JSONParser();
        Reader reader = new FileReader("C:\\Users\\rdjr\\Documents\\Empleos\\conf.json");
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        System.out.println(jsonObject);
        System.out.println("------");

        String jsonData = jsonObject.toString();
        String data = jsonData.replaceAll("\\[", "").replaceAll("\\]","")
                         .replaceAll("[{}]","").replaceAll("\"nomenclatura\":","")
                         .replaceAll("\"sftp\":","");

        //https://www.studytonight.com/java-examples/how-to-convert-string-to-arraylist-in-java
        ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(",")));
        list.forEach(System.out::println);
        System.out.println("--------");

        String nPDF = list.get(0);
        String nXML = list.get(1);
        String nTXT = list.get(2);

        String hostSFTP = list.get(5);
        String portSFTP = list.get(4);
        String userSFTP = list.get(6);
        String passwordSFTP = list.get(3);




    }

}