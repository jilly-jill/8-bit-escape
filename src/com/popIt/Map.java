package com.popIt;


import java.util.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

class Map {
    JSONParser parser = new JSONParser();
    private String currentRoom;
    private String roomDesc;
    private String itemDesc;

    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) { // ex: "north"
        this.currentRoom = currentRoom;
    }

    public String getRoomDesc() { return roomDesc; }

    public void setRoomDesc(String roomDesc) { this.roomDesc = roomDesc; }

    public String getItemDesc() { return itemDesc; }

    public void setItemDesc(String itemDesc) { this.itemDesc = itemDesc; }

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
                System.out.println("YOU ARE NOW IN " + getCurrentRoom());
                result = getCurrentRoom();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void roomInfo(){
        try {
            //instantiate parser to read file
            Object obj = parser.parse(new FileReader("data/map.json"));
            JSONObject jsonObject = (JSONObject)obj;
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String,String> roomMap = (HashMap<String,String>) jsonObject.get(getCurrentRoom());
            // iterate through jsonObject's key
            for(Object room : jsonObject.keySet()){
                // if the room key equals to the currentRoom, then set the room description to that value
                if(room.toString().equals(getCurrentRoom())){
                    setRoomDesc(roomMap.get("text"));
                    break;
                }
            }
            // display the value of lookRoom for 7 seconds then break
            System.out.println("More information: " + getRoomDesc());
            Thread.sleep(7000);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void itemInfo(){
        try {
            //instantiate parser to read file
            Object obj = parser.parse(new FileReader("data/map.json"));
            JSONObject jsonObject = (JSONObject)obj;
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String,String> roomMap = (HashMap<String,String>) jsonObject.get(getCurrentRoom());
            // iterate through jsonObject's key
            for(Object room : jsonObject.keySet()){
                // if the room key equals to the currentRoom, then set the item description to that value
                if(room.toString().equals(getCurrentRoom())){
                    setItemDesc(roomMap.get("text"));
                    break;
                }
            }
            // display the value of lookRoom for 7 seconds then break
            System.out.println("More information about the item: " + getItemDesc());
            Thread.sleep(7000);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}