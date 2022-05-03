package com.popIt;


import java.util.*;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;

class Map {
    // Global Variables
    JSONParser parser = new JSONParser();
    private String currentRoom;
    private String roomDesc;
    private String itemDesc;
    private final String map = "json/map.json";
    private final String zombie = "json/zombies.json";
    private Object obj;
    private Boolean isMiniGame = false;
    private String items;
    Player player = new Player();

    // Getters and Setters
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

    public Object getObj() { return obj;}

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Boolean getMiniGame() {
        return isMiniGame;
    }

    public void setMiniGame(Boolean miniGame) {
        isMiniGame = miniGame;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }


    public String getMap(String direction) {
        String result = "";
        JSONObject jsonObject;
        try {
            //instantiate parser to read file
            // TODO 5/3 Set minigame to false after exiting zombie map and look logic for rooms and items in zombies
            if (getCurrentRoom().equals("explosionsandzombies")) {
                InputStream inputTestJSON = getFileFromResourceAsStream(zombie);
                setObj(parser.parse(new InputStreamReader(inputTestJSON, "UTF-8")));
                setCurrentRoom("start");
                setMiniGame(true);
                }
                if (getMiniGame().equals(true)) {
                    jsonObject = (JSONObject) getObj();
                } else {
                    setObj(parser.parse(new FileReader(map)));
                    jsonObject = (JSONObject) getObj();
                }
                System.out.println("YOU WERE IN " + getCurrentRoom());
                // move jsonObject into a hashmap (to use built-in methods to move data)
                HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
                // iterate through jsonObject's key
                for (Object room : jsonObject.keySet()) {
                    // if the room key equals to the currentRoom
                    if (room.toString().equals(getCurrentRoom())) {
                        // set that room key value of the key (direction) to the currentRoom EX: if "north" in "start" then assign "north"'s value to currentRoom
                        setCurrentRoom(roomMap.get(direction));
                        break;
                    }
                    System.out.println("YOU ARE NOW IN " + getCurrentRoom());
                    result = getCurrentRoom();
                }
        }catch(Exception e){
                e.printStackTrace();
            }
            return result;
    }

        public void roomInfo () {
            try {
                //instantiate parser to read file

                if (getCurrentRoom().equals("explosionsAndZombies")) {
                    obj = parser.parse(new FileReader(zombie));
                    JSONObject jsonObject = (JSONObject) obj;
                    // move jsonObject into a hashmap (to use built-in methods to move data)
                    HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
                    // iterate through jsonObject's key
                    for (Object room : jsonObject.keySet()) {
                        // if the room key equals to the currentRoom, then set the room description to that value
                        if (room.toString().equals(getCurrentRoom())) {
                            setRoomDesc(roomMap.get("text"));
                            break;
                        }
                    }
                    // display the value of lookRoom for 7 seconds then break
                    System.out.println("More information: " + getRoomDesc());
                    Thread.sleep(7000);
                } else {
                    obj = parser.parse(new FileReader(map));
                    JSONObject jsonObject = (JSONObject) obj;
                    // move jsonObject into a hashmap (to use built-in methods to move data)
                    HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
                    // iterate through jsonObject's key
                    for (Object room : jsonObject.keySet()) {
                        // if the room key equals to the currentRoom, then set the room description to that value
                        if (room.toString().equals(getCurrentRoom())) {
                            setRoomDesc(roomMap.get("text"));
                            break;
                        }
                    }
                    // display the value of lookRoom for 7 seconds then break
                    System.out.println("More information: " + getRoomDesc());
                    Thread.sleep(7000);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void itemInfo () {
            try {
                //instantiate parser to read file
                Object obj = parser.parse(new FileReader("data/map.json"));
                JSONObject jsonObject = (JSONObject) obj;
                // move jsonObject into a hashmap (to use built-in methods to move data)
                HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
                // iterate through jsonObject's key
                for (Object room : jsonObject.keySet()) {
                    // if the room key equals to the currentRoom, then set the item description to that value
                    if (room.toString().equals(getCurrentRoom())) {
                        setItemDesc(roomMap.get("text"));
                        break;
                    }
                }
                // display the value of lookRoom for 7 seconds then break
                System.out.println("More information about the item: " + getItemDesc());
                Thread.sleep(7000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    private static InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = Map.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
