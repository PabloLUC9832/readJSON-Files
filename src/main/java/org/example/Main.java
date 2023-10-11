package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.Charsets;
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
        System.out.println("------jsonObject--------");

        String jsonData = jsonObject.toString();
        String data = jsonData.replaceAll("\\[", "").replaceAll("\\]","")
                         .replaceAll("[{}]","").replaceAll("\"nomenclatura\":","")
                         .replaceAll("\"sftp\":","");

        //https://www.studytonight.com/java-examples/how-to-convert-string-to-arraylist-in-java
        ArrayList<String> list = new ArrayList<>(Arrays.asList(data.split(",")));
        list.forEach(System.out::println);
        System.out.println("--------KEY:VALUE-----");

        //String nPDF = list.get(0);
        String nPDF = listToString(list,0);
        String nXML = listToString(list,1);
        String nTXT = listToString(list,2);

        String passwordSFTP = listToString(list,3);
        String portSFTP = listToString(list,4);
        String hostSFTP = listToString(list,5);
        String userSFTP = listToString(list,6);

        System.out.println(nPDF);
        System.out.println(nXML);
        System.out.println(nTXT);
        System.out.println(hostSFTP);
        System.out.println(portSFTP);
        System.out.println(userSFTP);
        System.out.println(passwordSFTP);

        //Lectura de archivos PDF, extrayendo la clave despues de las palabras Hola mundo

        File dir = new File("C:\\Users\\rdjr\\Documents\\Empleos\\galvan\\input");
        File[] files = dir.listFiles(new FilenameFilter()
        {
            // use anonymous inner class
            @Override
            public boolean accept(File dir, String name)
            {
                return name.toLowerCase().endsWith(".pdf");
            }
        });
        System.out.println("--------TEXT FROM PDFs-------");
        ArrayList<String> textsFromPDF = new ArrayList<>();
        for (File file : files)
        {
            /*
            int len = file.getAbsolutePath().length();
            String txtFilename = file.getAbsolutePath().substring(0, len - 4) + ".txt";
            // check whether txt file exists omitted

            try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(txtFilename), Charsets.UTF_8);
                 PDDocument document = PDDocument.load(file))
            {
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.writeText(document, out);
            }
            */
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            //Retrieving text from PDF document
            //List<String> text = Arrays.asList(stripper.getText(document));
            String text = stripper.getText(document);
            System.out.println(text);
            textsFromPDF.add(text);
            //Closing the document
            document.close();
        }
        System.out.println("------KEYS---------");

        //for (int i=0; i< textsFromPDF.size();i++) {
        ArrayList<String> keysFromPDF = new ArrayList<>();
        for (String s : textsFromPDF) {
            //System.out.println(s.replaceAll("Hola mundo","").replaceAll(",",""));
            keysFromPDF.add(s.replaceAll("Hola mundo","").replaceAll(",",""));
        }
        System.out.println(keysFromPDF);
        /*
        String aa = "" + textsFromPDF.get(1).replaceAll("\\[", "")
                                            .replaceAll(",","")
                                            .replaceAll("Hola mundo","");
        */
        //String aaa = Arrays.toString(aa.split(","));

        //System.out.println(aa);
        //System.out.println(aa.replaceAll("\\[", "").replaceAll("\\]",""));

        /*
        for (String s: textsFromPDF) {
            //System.out.println(Arrays.toString(s.split(",")));
            String a = Arrays.toString(new String[]{s.replaceAll("\\[", "").replaceAll("\\]", "")});
            String b = String.valueOf(a.split(","));
            System.out.println(b);
        }*/
        //System.out.println(textsFromPDF);


    }

    private static String listToString(List list,int index){

        String data = (String) list.get(index);
        String parts[] = data.split(":");
        String value = parts[1].replaceAll("\"","");
        return value;
    }

}