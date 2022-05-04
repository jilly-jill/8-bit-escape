package com.popIt.design;

import java.io.*;
import com.popIt.*;

public class Ascii {
    ReadFile readFile = new ReadFile();

    private static Boolean isPreviousLineString = false;

    public static Boolean getIsPreviousLineString() {
        return isPreviousLineString;
    }

    public static void setIsPreviousLineString(Boolean isPreviousLineString) {
        Ascii.isPreviousLineString = isPreviousLineString;
    }


    //TODO: Timing Variables for Scroll & Art
    private void generate(String pathName) {
        {
            try {

                InputStream inputTestFile = readFile.getFileFromResourceAsStream(pathName, Ascii.class);
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

    public void getText(String s){
        generate(s);
    }

}






