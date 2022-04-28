package com.popIt;

import java.util.*;
import com.popIt.design.*;
import java.util.*;

/* TODO: Resident Evil mini-game - Hallway - Walkie Talkie:
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


                    public void execute(){
                        System.out.println("Executing game...");
                        welcome();
                        while (!isOver()) {
                            try {
                                while (!isEndGamePlay()) {
                                    try {
                                        gamePlay();

                                        // if player inventory contains 3 tokens & player is in labyrinth center
                                        // else if player lives == 0
                                        // break.
                                        if(isOver()){
                                            break;
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    System.out.println("game is over= "+ isOver());
                                    System.out.println("end game play= " + isEndGamePlay());
                                }


                                if(isEndGamePlay()){
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
                        getInstruction();

                    }

                    private void getSplashTheme() {
                        //
                        Ascii.splashScreen();
                    }

                    private void getOpening(){
                        Ascii.opening();
                    }

                    private void getUsername() {
                        try {
                            boolean validInput = false;
                            System.out.print("Please enter your name: ");
                            while (!validInput) {
                                String username = scanner.nextLine();
                                if (username.matches("[a-zA-Z]{2,15}")) {
                                    player.setUsername(username); // TODO: Create a player class
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

                    private void getInstruction() {
                        // sout for story
                        // sout for instructions here
                        getCommands();
                    }

                    private void getCommands() {
                        // sout commands
                        System.out.println(
                                "Commands:\n go [direction]\n get [item] \n engage [character]\n use [item]\n look [item, location]\n show [menu]");
                    }

                    private void showStatus() {
                        System.out.println("ROOM status"); // room status => current room and room inventory
                        System.out.println("INVENTORY status"); // player status => current inventory and player lives/health

                    }

                    private void showMenu() {

                        System.out.println("Press Q to quit , R to restart, or C to continue");
                        // what would you like to do?
                        // scanner input
                        // if input == q
                        // quit = true
                        // if input == r
                        // restart = true
                        // if input == c
                        // sout exiting menu...

                    }

                    private void gamePlay() {
                        //read map json
                        // inventory [] artifact
                        // inventory [] items
                        // String currentRoom = "";

                        while (true) {
                            showStatus();
                            String move = "";
                            while (move.equals("")) {
                                System.out.println("enter command >");
                                move = scanner.nextLine().toLowerCase();
                            }

                            // String inputGamePlay = scanner.nextLine().toLowerCase();
                            // do game stuff

                            if (move.matches("show menu|menu")) {
                                showMenu();
                                String input = scanner.nextLine().toLowerCase();
                                if (input.matches("q|quit")) {
                                    System.out.println("Are you sure you want to QUIT?");
                                    String inputQuit = scanner.nextLine().toLowerCase();
                                    if(inputQuit.matches("y|yes")){
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

                            if (move.matches("go ")) {
                                // currentRoom = map.get(currentRoom);
                                // TODO: match key in nested JSON
                                // currentRoom = "the new room"; //  from value of JSON key
                            }


                            // if dead => break;
                            // if win => break;
                        }


                    }

                }
