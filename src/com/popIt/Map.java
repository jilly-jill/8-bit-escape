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
        String result = "";
        try {
            //instantiate parser to read file
            Object obj = parser.parse(new FileReader("data/map.json"));
            JSONObject jsonObject = (JSONObject)obj;

            System.out.println("YOU WERE IN " + getCurrentRoom());
            String currentR = getCurrentRoom();

            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String,String> roomMap = (HashMap<String,String>) jsonObject.get(getCurrentRoom());
            // iterate through jsonObject's key
            for(Object room : jsonObject.keySet()){
                // if the room key equals to the currentRoom
                if(room.toString().equals(getCurrentRoom())){
                    // set that room key value of the key (direction) to the currentRoom EX: if "north" in "start" then assign "north"'s value to currentRoom
                    setCurrentRoom(roomMap.get(direction));
                    break;
                }

            }
            //if currentRoom is null then do something Todo: Work on NULL VALUES 4/29/22
            if(getCurrentRoom() == null){
                System.out.println("YOU ARE NOW IN " + getCurrentRoom());
                setCurrentRoom(currentR);
            } else {
                result = getCurrentRoom();
            }



        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
