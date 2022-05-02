package com.popIt;

import java.util.*;
import com.popIt.design.*;

/* 04/29 */

/* TODO 04-29 - SPRINT 2: Resident Evil mini-game - Hallway - Walkie Talkie:
    If user gets walkie talkie, iterate through inventory once item is set
        if user has walkie talkie, have walkie talkie print 'message' associated w/ json object
        once message is printed, pop walkie-talkie from user inventory
    ie:
        get(item)
        if player.getInventory.matches["walkie talkie"]{
            print(however we call the parsed json - walkie talkie -> message)
            remove item from json (need to see parse docs before working out logic)
        else{.....}


 */
public class Game {
    private Player player = new Player();
    private Map map = new Map();
    private final Scanner scanner = new Scanner(System.in);
    private boolean isOver;
    private boolean endGamePlay;

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public boolean isEndGamePlay() {
        return endGamePlay;
    }

    public void setEndGamePlay(boolean endGamePlay) {
        this.endGamePlay = endGamePlay;
    }

    public void execute() {
        System.out.println("Executing game...");
        welcome();
        while (!isOver()) {
            try {
                while (!isEndGamePlay()) {
                    try {
                        gamePlay();
                        if (isOver()) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("game is over= " + isOver());
                    System.out.println("end game play= " + isEndGamePlay());
                }
                if (isEndGamePlay()) {
                    setEndGamePlay(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game over");
    }

    public void welcome() {

        getSplashTheme();
        getOpening();
        getUsername();
        getHelpMenu();

    }

    private void getSplashTheme() {

        Ascii.splashScreen();

    }

    private void getOpening() {

        Ascii.opening();

    }

    private void getUsername() {
        try {
            boolean validInput = false;
            System.out.print("Please enter your name: ");
            while (!validInput) {
                String username = scanner.nextLine();
                if (username.matches("[a-zA-Z]{2,15}")) {
                    player.setUsername(username);
                    validInput = true;
                } else {
                    System.out.println(
                            "Please enter a valid user name between 2 and 15 characters (numbers not allowed)");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getHelpMenu() {
        Ascii.commands();
    }

    private void showStatus() {
        System.out.println("\n ===== " + player.getUsername() + "'s Current Status =====\n" +
                "Current Room: " + map.getCurrentRoom().toUpperCase() + "\n" +
                "Current Lives: " + player.getLives() + "\n" +
                "Current Inventory: " + player.getInventory() + "\n" +
                "Items in Room: map.getItems()\n" +
                "You can Move: map.availableDirections()\n" +
                "=====\n");

    }

    private void win() {
        System.out.println("YOU WIN");
    }

    private void gamePlay() {
        player.setLives(3);
        player.setInventory(null);
        map.setCurrentRoom("start");

        while (true) {
            showStatus();

            String move = "";
            while (move.equals("")) {
                System.out.println("Enter Command: > ");
                move = scanner.nextLine().toLowerCase();
            }

            String[] moveArray = move.split(" ");
            String[] itemArray = move.split(" ");
            if (moveArray[0].equals("go")) {
                if (moveArray[1].equals(map.getMap(moveArray[1]))) {
                    map.setCurrentRoom(map.getCurrentRoom());
                }
                else if (itemArray[0].equals("get")) {
                    if(itemArray[1].equals(map.getMap(itemArray[1]))){
                        map.setItems(map.getItems());
                        }
                    }
                }

                    if (move.matches("h|help")) {
                        String input = scanner.nextLine().toLowerCase();
                        getHelpMenu();
                        if (input.matches("q|quit")) {
                            System.out.println("Are you sure you want to QUIT?");
                            String inputQuit = scanner.nextLine().toLowerCase();
                            if (inputQuit.matches("y|yes")) {
                                setOver(true);
                                break;
                            } else {
                                continue;
                            }
                        }
                        if (input.matches("r|restart|")) {
                            setEndGamePlay(true);
                            break;
                        }
                        if (input.matches("c|continue|")) {
                            System.out.println("continuing game");
                            continue;
                        }

                try {
                    if (player.getInventory().contains("token") && map.getCurrentRoom().equalsIgnoreCase("mazeCenter")) {
                        win();
                        isOver();
                    }

                } catch(Exception e){
                        e.getStackTrace();
                    }

                }
            }
        }
    }









