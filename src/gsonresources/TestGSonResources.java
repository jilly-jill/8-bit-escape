package gsonresources;

import com.google.gson.*;
import com.popIt.*;

import java.io.*;
import java.util.*;

public class TestGSonResources {

    public static void main(String[] args) {
//        Game game = new Game();
//        game.execute();
        printTestDataGSON();

    }
    public static void printTestDataGSON() {
        Gson gson = new Gson();
        String s = "gson/map.json";
        InputStream inputTestJSON = getFileFromResourceAsStream(s);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputTestJSON, "UTF-8"))){
            Map<String, Map<String, Object>> map = gson.fromJson(reader, Map.class);
            System.out.println(map.keySet().size());
            System.out.println(map.keySet().toArray()[0]);
            System.out.println(map.keySet().toArray());
        } catch (IOException ioe){
            System.out.println("unable to read file " + s);
        }
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = TestGSonResources.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

}

