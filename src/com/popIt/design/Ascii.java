package com.popIt.design;

import java.io.*;

    public abstract class Ascii {

    /* ascii art class pulls ascii art from .txt file via
    BufferReader class
     */

        //TODO: Timing Variables for Scroll & Art
        public static void generate(String pathName){
            {
                try {
                    //having issue w/ path file
                    File file = new File(pathName);
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String check;
                    while (true) {
                        try {
                            if ((check = reader.readLine()) != null) {
                                System.out.println(check);
                            }
                            else{
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }

        }
        //TODO: Path Names -
        public static void splashScreen(){

            String pathName = "C:\\Users\\jilli\\OneDrive\\Desktop\\8-bit-escape\\src\\com\\popIt\\design\\Splash.txt";
            generate(pathName);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void opening(){
            String pathName = "C:\\Users\\jilli\\OneDrive\\Desktop\\8-bit-escape\\src\\com\\popIt\\design\\Opening.txt";
            generate(pathName);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } public static void commands(){
            String pathName = "C:\\Users\\jilli\\OneDrive\\Desktop\\8-bit-escape\\src\\com\\popIt\\design\\Opening.txt";
            generate(pathName);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



