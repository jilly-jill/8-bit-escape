package com.popIt.design;

import java.io.*;

public class Ascii {
    private static Boolean isPreviousLineString = false;

    public static Boolean getIsPreviousLineString() {
        return isPreviousLineString;
    }

    public static void setIsPreviousLineString(Boolean isPreviousLineString) {
        Ascii.isPreviousLineString = isPreviousLineString;
    }


    //TODO: Timing Variables for Scroll & Art
    private static void generate(String pathName) {
        {
            try {

                InputStream inputTestFile = getFileFromResourceAsStream(pathName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputTestFile, "UTF-8"));

                String check;

                while (true) {
                    try {
                        // if readline is not null
                        if ((check = reader.readLine()) != null) {
                            //isPreviousLineString true
                            setIsPreviousLineString(true);
                            System.out.println(check);
                            // else if readline is null and isPreviousLineString true then sout empty string
                        } else if((reader.readLine()) != null && getIsPreviousLineString().equals(true)) {
                            setIsPreviousLineString(false);
                            System.out.println(" ");
                        } else {
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }


    public static void getText(String s){
        generate(s);
    }


    // second time using this method => change to class and call method here.
    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = Ascii.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}