package com.popIt;

import java.io.*;

public abstract class Ascii {

        /* ascii art class pulls ascii art from .txt file via
        BufferReader class
         */

    //TODO: Timing Variables for Scroll & Art
    public static void generate(String pathName) {

        InputStream inputTestFile = getFileFromResourceAsStream(pathName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputTestFile, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        }


    //TODO: Path Names -
    public static void splashScreen() {

        String pathName = "json/splash.txt";
        generate(pathName);
/*            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }*/
    }

    public static void opening() {
        String pathName = "json/opening.txt";
        generate(pathName);
//            try {
////                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
    }

    public static void commands() {
        String pathName = "json/directions.txt";
        generate(pathName);
/*           try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
*/
    }

    public static void win() {
        String pathName = "json/win.txt";
        generate(pathName);
    }

    public static void lose() {
        String pathName = "json/lose.txt";
        generate(pathName);
    }

    public static void ghost() {
        String pathName = "json/ghost.txt";
        generate(pathName);
    }
    private static InputStream getFileFromResourceAsStream(String pathName) {
        ClassLoader classLoader = Ascii.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(pathName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + pathName);
        } else {
            return inputStream;
        }
    }
}




