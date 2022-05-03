package com.popIt.design;

import java.io.*;

    public abstract class Ascii {

        /* ascii art class pulls ascii art from .txt file via
        BufferReader class
         */
        public static void main(String[] args) {
            commands();
        }

        //TODO: Timing Variables for Scroll & Art
        public static void generate(String pathName) {
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
                            } else {
                                break;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

        //TODO: Path Names -
        public static void splashScreen() {

            String pathName = "resources/gson/splash.txt";
            generate(pathName);
/*            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }*/
        }

        public static void opening() {
            String pathName = "resources/gson/opening.txt";
            generate(pathName);
//            try {
////                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
        }

        public static void commands() {
            String pathName = "resources/gson/directions.txt";
            generate(pathName);
/*           try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
*/
        }

        public static void win() {
            String pathName = "resources/gson/win.txt";
            generate(pathName);
        }

        public static void lose() {
            String pathName = "resources/gson/lose.txt";
            generate(pathName);
        }

        public static void ghost() {
            String pathName = "resources/gson/ghost.txt";
            generate(pathName);
        }
    }




