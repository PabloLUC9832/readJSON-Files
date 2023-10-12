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
        //Map<String, String> map = new HashMap<String, Integer>();

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
            textsFromPDF.add(file.getName());
            textsFromPDF.add(text.trim());
            //Closing the document
            document.close();
        }
        System.out.println("------KEYS---------");

        ArrayList<String> keysFromPDF = new ArrayList<>();
        for (String s : textsFromPDF) {
            //keysFromPDF.add(s);
            keysFromPDF.add(s.replaceAll(" ","")
                    .replaceAll("Holamundo","").replaceAll(",",""));
                    //.replaceAll(",",""));
                    //.replaceAll(" ",""));
        }
        System.out.print(keysFromPDF);

        //Manejo de archivos, renombrar los pdfs con las keys y los sufijos, y guardarlos en la carpeta salida.
        OutputStream os = null;
        InputStream is = null;
        System.out.println("------DOCS-KEYS---------");
        for (File file : files)
        {
            /*
            for (String s : keysFromPDF) {
                System.out.println("*-**");
                System.out.println(s);
                if (file.getName().equals( s.get )){
                    System.out.println(file.getName()+"_"+s.trim()+"_"+nPDF);
                }

            }
             */
            //System.out.println(file.getName().replaceAll(" ",""));
            //System.out.println(keysFromPDF.get(0));

            for(int i=0;i<keysFromPDF.size();i++){
                if (file.getName().replaceAll(" ","").equals( keysFromPDF.get(i) )){
                    //System.out.println(file.getName().trim()+"_"+keysFromPDF.get(i)+"_"+nPDF);
                    is = new FileInputStream(file);
                    os = new FileOutputStream(
                            new File("C:\\Users\\rdjr\\Documents\\Empleos\\galvan\\output\\"+keysFromPDF.get(i+1)+"_"+nPDF+".pdf"));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                }
            }

        }
        /*
        for(int i=0;i<files.length;i++){

            for(int j=i;j<keysFromPDF.size();j++){
                System.out.println(files[i].getName()+"_"+keysFromPDF.get(j).trim()+"_"+nPDF);
            }

        }
        */
    }

    private static String listToString(List list,int index){

        String data = (String) list.get(index);
        String parts[] = data.split(":");
        String value = parts[1].replaceAll("\"","");
        return value;
    }

}