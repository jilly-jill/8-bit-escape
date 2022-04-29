package com.popIt;


import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

class Map {
    JSONParser parser = new JSONParser();
    private String currentRoom;

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) { // ex: "north"
        this.currentRoom = currentRoom;
    }


    public String getMap(String direction){
        //Todo: Work on NULL VALUES
        String result = "";
        try {
            Object obj = parser.parse(new FileReader("data/map.json"));
            JSONObject jsonObject = (JSONObject)obj;

            System.out.println("YOU WERE IN " + getCurrentRoom());

            HashMap<String,String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());

            for(Object room : jsonObject.keySet()){

                if(getCurrentRoom().equals(room.toString())){
                    setCurrentRoom(roomMap.get(direction));
                    break;
                }

            }
            if(getCurrentRoom().equals(null)){
                System.out.println("Please enter valid direction");
            }

            System.out.println("YOU ARE NOW IN " + getCurrentRoom());
            result = getCurrentRoom();

        } catch(Exception e) {
            e.printStackTrace();
        }
        if(getCurrentRoom().equals(null)){
            result = "Please enter valid direction";
        }
        return result;
    }
}
