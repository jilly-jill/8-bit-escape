package com.popIt;

import org.json.simple.JSONObject;
import org.junit.*;
import static org.junit.Assert.*;

public class GameTest {

    private Game game = new Game();
    private Player player = new Player();
    private GameMap gameMap = new GameMap();

//    @Before
//    public void set(){
//
//    }

    @Test
    public void testZombieJson(){
        System.out.println("testZombieJson");
        gameMap.setCurrentRoom("explosionsandzombies");

        // valid response
        String response = gameMap.getMap("north");
        String test = "hall";
        assertEquals(test, response);

        // invalid response
        test = "start";
        assertNotEquals(test,response);
    }

    @Test
    public void testGameMap(){
        System.out.println("testGameMap");
        gameMap.setCurrentRoom("start");
        // valid response
        String response = gameMap.getMap("north");
        String test = "corridor1";
        assertEquals(test, response);

        // invalid response
        test = "corridor100";
        assertNotEquals(test,response);

    }

    @Test
    public void testGameMapToZombie_shouldMoveToZombieMap(){
        gameMap.setCurrentRoom("corridor11");
        gameMap.getMap("east");

        // going east from "corridor11" leads to "explosionsandzombies" room
        String response = gameMap.getCurrentRoom();
        String test = "explosionsandzombies";
        assertEquals(test,response);

        // if going north from "start" of zombie map then player should be in "hall"
        gameMap.getMap("north");
        response = gameMap.getCurrentRoom();
        test = "hall";
        assertEquals(test, response);

        // after entering zombie map, if entering a non-existing direction then currentRoom will be set to "start"
        gameMap.getMap("south");
        response = gameMap.getCurrentRoom();
        test = "start";
        assertEquals(response,test);
    }

    @Test
    public void corridor1FromMapTestLookItems_shouldReturnNoItemsResponse(){
        gameMap.setCurrentRoom("corridor1");
        gameMap.setItemDesc(null);
        gameMap.itemInfo();
        String test = "There are no items in here!";

    }

}
