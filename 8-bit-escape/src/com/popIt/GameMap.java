package com.popIt;

import java.util.*;
import java.io.*;
import com.popIt.design.*;
import org.json.simple.*;
import org.json.simple.parser.*;

class GameMap {
    // Global Variables
    private final static String step1 = "sound/footStep1.wav";
    private final static String step2 = "sound/footStep2.wav";
    private JSONParser parser = new JSONParser();
    private ReadFile readFile = new ReadFile();
    private Ascii ascii = new Ascii();
    private String currentRoom;
    private String roomDesc;
    private String lookRoomText;
    private String itemDesc;
    private final String mainMap = "json/map.json";
    private final String zombie = "json/zombies.json";
    private String itemDescOne;
    private String itemDescTwo;
    private String itemList;
    private ArrayList inventory = new ArrayList();
    private Object obj;
    private Boolean isMiniGame = false;
    private String items;
    private String roomId;
    private final int timer = 5000;
    private Boolean isMonster = false;
    private JSONObject jsonObject;
    private SoundPlayer sound = new SoundPlayer();

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

    public String getLookRoomText() { return lookRoomText; }

    public void setLookRoomText(String lookRoomText) { this.lookRoomText = lookRoomText; }

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

    public ArrayList getInventory() { return inventory; }

    public void setInventory(ArrayList inventory) { this.inventory = inventory; }

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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Boolean getMonster() {
        return isMonster;
    }

    public void setMonster(Boolean monster) {
        isMonster = monster;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
    //business methods

    public int getTimer() {
        return timer;
    }

    // reads json file according to the room the player is in
    private JSONObject createJson() throws IOException, ParseException {
        //private Object obj;
        InputStream inputTestJSON;
        // if current room is zombies then read zombie.json
        if (getCurrentRoom().equals("explosionsandzombies")) {
            inputTestJSON = readFile.getFileFromResourceAsStream(zombie, GameMap.class);
            setObj(parser.parse(new InputStreamReader(inputTestJSON, "UTF-8")));

            setCurrentRoom("start");
            setMiniGame(true);
        }
        if (getMiniGame().equals(true)) {
            setJsonObject((JSONObject) getObj());
        } else {
            // get map.json
            inputTestJSON = readFile.getFileFromResourceAsStream(mainMap, GameMap.class);
            setObj(parser.parse(new InputStreamReader(inputTestJSON, "UTF-8")));

            setJsonObject((JSONObject) getObj());

        }
        return getJsonObject();
    }

    public String getMap(String direction) {
        String result = "";
        String previousRoom = "";
        InputStream inputWavSound;

        while (true) {
            try {
                // createJson object
                setJsonObject(createJson());

                // move jsonObject into a hashmap (to use built-in methods to move data)
                HashMap<String, String> roomMap = (HashMap<String, String>) getJsonObject().get(getCurrentRoom());
                // iterate through jsonObject's key
                for (Object room : getJsonObject().keySet()) {
                    // if current is start and key not in then set currentRoom to start
                    // if the room key equals to the currentRoom
                    if (room.toString().equals(getCurrentRoom())) {
                        // set that room key value of the key (direction) to the currentRoom EX: if "north" in "start" then assign "north"'s value to currentRoom
                        setCurrentRoom(roomMap.get(direction));
                        for(int i = 0; i < 3; i++) {

                            sound.play(step1, true, 0);
                            Thread.sleep(0200);
                            sound.play(step2, true, 0);

                            Thread.sleep(0200);
                        }
                        if (getCurrentRoom() != null) {
//                            System.out.println("YOU ARE NOW IN " + getCurrentRoom());
                        }
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
                    System.out.println("Can't go that way...");
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
        try {
            setJsonObject(createJson());
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String, String> roomMap = (HashMap<String, String>) getJsonObject().get(getCurrentRoom());
            // iterate through jsonObject's key
            for (Object room : getJsonObject().keySet()) {
                // if the room key equals to the currentRoom
                if (room.toString().equals(getCurrentRoom())) {
                    // Setting the room text value to room description
                    setRoomDesc(roomMap.get("text"));
                    //getRoomId and concat. pathname file - calling ascii class to get pathname and use to generate player map
                    setRoomId(roomMap.get("id"));
                    String pathName = "text/" + getRoomId() + ".txt";
                    ascii.getText(pathName);
                    break;
                }
            }
            System.out.println(getRoomDesc());
//            Thread.sleep(getTimer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lookRoomInfo() {
        try {
            setJsonObject(createJson());
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String, String> roomMap = (HashMap<String, String>) getJsonObject().get(getCurrentRoom());
            // iterate through jsonObject's key
            for (Object room : getJsonObject().keySet()) {
                // if the room key equals to the currentRoom
                if (room.toString().equals(getCurrentRoom())) {
                    // Setting the room text value to room description
                    setLookRoomText(roomMap.get("lookroom"));
                    //getRoomId and concat. pathname file - calling ascii class to get pathname and use to generate player map
                    setRoomId(roomMap.get("id"));
                    String pathName = "text/" + getRoomId() + ".txt";
                    ascii.getText(pathName);
                    break;
                }
            }
            System.out.println(getLookRoomText());
            Thread.sleep(timer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void itemInfo() {
        setItemDesc(" ");
        setItemDescOne(" ");
        setItemDescTwo(" ");
        setItemList(" ");
        try {
            // create jsonObject
            setJsonObject(createJson());
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String, String> roomMap = (HashMap<String, String>) getJsonObject().get(getCurrentRoom());
            // iterate through jsonObject's key
            for (Object room : getJsonObject().keySet()) {
                // if the room key equals to the currentRoom
                if (room.toString().equals(getCurrentRoom())) {
                    if (roomMap.get("items") != null) {
                        if (roomMap.get("items") != null) {
                            setItemList(roomMap.get("items"));
                        }
                        if (roomMap.get("lookitem") != null) {
                            setItemDesc(roomMap.get("lookitem"));
                        }
                        if (roomMap.get("lookitemone") != null) {
                            setItemDescOne(roomMap.get("lookitemone"));
                        }
                        if (roomMap.get("lookitemtwo") != null)
                            setItemDescTwo(roomMap.get("lookitemtwo"));
                        break;
                    } else {
                        System.out.println("There are no items in here!");
                    }
                }
            }
            System.out.println(getItemList() + "\n" +
                    getItemDesc() + "\n" +
                    getItemDescOne() + "\n" +
                    getItemDescTwo());
            Thread.sleep(getTimer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retrieveItems(String retrieve) {

        try {
            setJsonObject(createJson());
            // move jsonObject into a hashmap (to use built-in methods to move data)
            HashMap<String, String> roomMap = (HashMap<String, String>) getJsonObject().get(getCurrentRoom());
            // iterate through jsonObject's key
            for (Object room : getJsonObject().keySet()) {
                // if the room key equals to the currentRoom we will be getting items from
                if (room.toString().equals(getCurrentRoom())) {
                    // if the items value contains movearray[1], then add movearray[1] to the inventory
                    if(roomMap.get("items").contains(retrieve)) {
                        inventory.add(retrieve);

                        // Custom sound based on object
                        sound.play(roomMap.get("sound"), true, 0);

                    }
                    if (!roomMap.get("items").contains(retrieve)) {
                        System.out.println("That item is not here.");
                    }
//                    clip.start();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void removeItems(String dropItem) {
        try{
            if(getInventory().contains(dropItem)){
                inventory.remove(dropItem);
            }
            else{ System.out.println("You don't have that item!");}
        }catch (Exception e){
        }
    }

    }
