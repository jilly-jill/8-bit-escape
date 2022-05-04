package com.popIt;


import java.util.*;
import java.io.*;

import com.popIt.design.*;
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
    private String path = "json/";
    private String itemDescOne;
    private String itemDescTwo;
    private String itemList;
    private Object obj;
    private Boolean isMiniGame = false;
    private String items;
    private String id;
    Player player = new Player();

    // Getters and Setters
    public String getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(String currentRoom) { // ex: "north"
        this.currentRoom = currentRoom;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemDescOne() {
        return itemDescOne;
    }

    public void setItemDescOne(String itemDescOne) {
        this.itemDescOne = itemDescOne;
    }

    public String getItemDescTwo() {
        return itemDescTwo;
    }

    public void setItemDescTwo(String itemDescTwo) {
        this.itemDescTwo = itemDescTwo;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }

    public Object getObj() {
        return obj;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMap(String direction) {
        String result = "";
        String previousRoom = "";
        JSONObject jsonObject;
        while (true) {
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

                    InputStream inputTestJSON = getFileFromResourceAsStream(map);
                    setObj(parser.parse(new InputStreamReader(inputTestJSON, "UTF-8")));

                    jsonObject = (JSONObject) getObj();

                }
                System.out.println("YOU WERE IN " + getCurrentRoom());
                // move jsonObject into a hashmap (to use built-in methods to move data)
                HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
                // iterate through jsonObject's key
                for (Object room : jsonObject.keySet()) {
                    // if current is start and key not in then set currentRoom to start
                    // if the room key equals to the currentRoom
                    if (room.toString().equals(getCurrentRoom())) {
                        // set that room key value of the key (direction) to the currentRoom EX: if "north" in "start" then assign "north"'s value to currentRoom
                        setCurrentRoom(roomMap.get(direction));
                        System.out.println("YOU ARE NOW IN " + getCurrentRoom());
                        // set current room as result
                        result = getCurrentRoom();
                        break;
                    } else {
                        // set current room as previous room
                        previousRoom = getCurrentRoom();
                    }
                }

                // currentRoom is start and invalid direction then set current room back to start
                if (Objects.equals(getCurrentRoom(), "")) {
                    setCurrentRoom("start");
                    previousRoom = getCurrentRoom();
                    // if current room is null then set previous room as current
                } else if (getCurrentRoom() == null) {
                    System.out.println("Please enter valid room");
                    setCurrentRoom(previousRoom);
                }
                break;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

    public void roomInfo() {
        JSONObject jsonObject;
        try {
            //instantiate parser to read file
            // TODO 5/3 Set minigame to false after exiting zombie map and look logic for rooms and items in zombies
            if (getCurrentRoom().equals("explosionsandzombies")) {
                setObj(parser.parse(new FileReader(zombie)));
                setCurrentRoom("start");
                setMiniGame(true);
            }
            if (getMiniGame().equals(true)) {
                jsonObject = (JSONObject) getObj();
            } else {
                setObj(parser.parse(new FileReader(map)));
                jsonObject = (JSONObject) getObj();
            }
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
            // iterate through jsonObject's key
            for (Object room : jsonObject.keySet()) {
                // if the room key equals to the currentRoom
                if (room.toString().equals(getCurrentRoom())) {
                    // Setting the room text value to room description
                    setRoomDesc(roomMap.get("text"));
                    break;
                }
            }
            System.out.println(getRoomDesc());
            Thread.sleep(8000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemInfo() {
        JSONObject jsonObject;
        setItemDesc(" ");
        setItemDescOne(" ");
        setItemDescTwo(" ");
        setItemList(" ");
        try {
            //instantiate parser to read file
            if (getCurrentRoom().equals("explosionsandzombies")) {
                setObj(parser.parse(new FileReader(zombie)));
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
                    //if the key value is equal to
                    if (roomMap.get("lookitem") != null) {
                        setItemDesc(roomMap.get("lookitem"));
                    }
                    if (roomMap.get("lookitemone") != null) {
                        setItemDescOne(roomMap.get("lookitemone"));
                    }
                    if (roomMap.get("lookitemtwo") != null)
                        setItemDescTwo(roomMap.get("lookitemtwo"));
                    break;
                }
            }
            System.out.println(getItemDesc() + "\n" +
                    getItemDescOne() + "\n" +
                    getItemDescTwo());
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//Function for correct player map to generate based off of current gameplay location.
//     public void roomPlayerMap() {
//         JSONObject jsonObject;
//         try {
//             //instantiate parser to read file
//             if (getCurrentRoom().equals("explosionsandzombies")) {
//                 setObj(parser.parse(new FileReader(zombie)));
//                 setCurrentRoom("start");
//                 setMiniGame(true);
//             }
//             if(getMiniGame().equals(true)) {
//                 jsonObject = (JSONObject) getObj();
//             } else {
//                 setObj(parser.parse(new FileReader(map)));
//                 jsonObject = (JSONObject) getObj();
//             }
//             HashMap<String, String> roomMap = (HashMap<String, String>) jsonObject.get(getCurrentRoom());
//             // iterate through jsonObject's key
//             for (Object room : jsonObject.keySet()) {
//                 // if the room key equals to the currentRoom
//                 if (room.toString().equals(getCurrentRoom())) {
//                     //if the key value is equal to
//                     if (roomMap.get("id") != null) {
//                         setId(roomMap.get("id"));
//                         String roomMapPath = path + getId();
//                         System.out.println(roomMapPath);
//                         //Ascii.getText(roomMapPath);
//                         break;
//                     }
//                 }
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//     }

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
