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

    private final Player player = new Player();
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

    /* 04-29 SPRINT 1 - Removed getInstructions() changed function to getHelpMenu() for code clarity
                    -> code was single call to getCommands() - JS */
    private void getHelpMenu() {
        Ascii.commands();
    }

    // TODO - JS - 04/29
    private void showStatus() {
        System.out.println("\n ===== " + player.getUsername() + "'s Current Status =====\n" +
                "Current Room: " + map.getCurrentRoom() + "\n" +
                "Current Lives: " + player.getLives() + "\n" +
                "Current Inventory: " + player.getInventory() + "\n" +
                "=====\n");

    }

    private void win() {
        System.out.println("YOU WIN");
    }

    private void gamePlay() {

        player.setInventory(null);
        map.setCurrentRoom("start");

        while (true) {
            showStatus();

            String move = "";
            while (move.equals("")) {
                //check for incorrect input here
                System.out.println("Enter Command: > ");
                move = scanner.nextLine().toLowerCase();
            }

            String[] moveArray = move.split(" ");
            if (moveArray[0].equals("go")) {
                if (moveArray[1].equals(map.getMap(moveArray[1]))) {
                    map.setCurrentRoom(map.getCurrentRoom());
                }
            }

            if (move.matches("H|h")) {
                getHelpMenu();
                String input = scanner.nextLine().toLowerCase();
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
            }

            if (player.getInventory().contains("token") && map.getCurrentRoom().contains("mazecenter")) {
                win();
                isOver();
                break;
            } else {
            }
        }
    }
}







