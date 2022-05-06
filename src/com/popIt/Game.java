package com.popIt;

import java.io.IOException;
import java.util.*;
import com.popIt.design.*;

import javax.sound.sampled.Clip;

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
    private SoundPlayer sound = new SoundPlayer();
    private final Player player = new Player();
    private final Scanner scanner = new Scanner(System.in);
    private GameMap gameMap = new GameMap();
    private Ascii ascii = new Ascii();
    private boolean isOver;
    private boolean endGamePlay;
    private boolean checkWin;
    private final Clip openSound = sound.play("Resources/sound/StarWars60.wav", true, 0);


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

    public boolean isCheckWin() {
        return checkWin;
    }

    public void setCheckWin(boolean checkWin) {
        this.checkWin = checkWin;
    }

    public void execute()  {
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
        getMenu();

    }

    private void getSplashTheme() {
        ascii.getText("text/splash.txt");
    }

    private void getOpening() {
        ascii.getText("text/opening.txt");

    }

    private void getUsername() {
        try {
            boolean validInput = false;
            System.out.print("Please enter your name: ");
            while (!validInput) {
                String username = scanner.nextLine();
                if (username.matches("[a-zA-Z]{2,15}")) {
                    player.setUsername(username.substring(0, 1).toUpperCase() + username.substring(1).toLowerCase());
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

    private void getMenu() {
        ascii.getText("text/directions.txt");
    }

    private void showStatus() {
        //show player and current room status
        //TODO: get/set logic for items
        gameMap.roomInfo();
        System.out.println( "\n ========== " + player.getUsername() + "'s Current Status ==========\n" +
                "  Current Room: " + gameMap.getCurrentRoom() + "\n" +
                "  Current Lives: " + player.getLives() + "\n" +
                "  Current Inventory: " + gameMap.getInventory() + "\n" +
                "========================================\n");

    }

    private boolean checkWin() {
        String currentRoom = gameMap.getCurrentRoom();
        if (currentRoom.equals("mazecenter")) {
            checkWin = true;
            ascii.getText("text/win.txt");;
        }else if(player.getLives() < 1){
            checkWin = true;
            ascii.getText("text/lose.txt");;
        }else {
            checkWin = false;
        }
        return checkWin;
    }


    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /*JS - 05/03 - Randomize function generates number 1-8,
    if number is >= 1 an ascii image of a ghost appears and player lives are set- 1
    player.getLives() is returned with current value
    Code checked, sout remains if you want to check generated digits and verify logic works*/
    private int randomize() {
        double digit = Math.random() * 8;
        if(digit <= 2 && gameMap.getCurrentRoom().matches("zombiesandexplosions|start|hall|infirmary|library|kitchen|basement")) {
            ascii.getText("text/notnem.txt");
            player.setLives(player.getLives() -1);
            return player.getLives();
        }else if(digit <= 2) {
            ascii.getText("text/ghost.txt");
            player.setLives(player.getLives() -1);
            System.out.println("NOTNEMESIS APPEARS! YOU ARE ");
            return player.getLives();
        }
        return player.getLives();
    }

    /*JS - 05/03 - if the current room contains "trap" - ascii.ghost generates ghost image and player loses 1 life
     */
    private int trap() {
        try {
            if (gameMap.getCurrentRoom().contains("trap")) {
                ascii.getText("text/ghost.txt");
                player.setLives(player.getLives() - 1);
            }
            return player.getLives();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return player.getLives();
    }


    private void gamePlay() {
        player.setLives(5);

        gameMap.setCurrentRoom("explosionsandzombies");
        openSound.stop();
        Clip themeSong = sound.play("Resources/sound/CantinaBand60.wav", true, 0);
        while (true) {
            clearScreen();
            showStatus();
            String move = "";
            while (move.equals("")) {
                System.out.println("enter command >");
                move = scanner.nextLine().toLowerCase();
            }
            String[] moveArray = move.split(" ");
            if (moveArray[0].equals("go")) {
                if (moveArray[1].equals(gameMap.getMap(moveArray[1]))) {
                    gameMap.setCurrentRoom(gameMap.getCurrentRoom());
                }
            } else if (moveArray[0].equals("look")) {
                if (moveArray[1].equals(gameMap.getCurrentRoom())) {
                    gameMap.lookRoomInfo();
                } else if (moveArray[1].equals("items")) {
                    gameMap.itemInfo();
                }
            } else if (moveArray[0].matches("get|grab")) {
                gameMap.retrieveItems(moveArray[1]);

            } else if (moveArray[0].matches("drop|remove")) {
                    gameMap.removeItems(moveArray[1]);
            } else if (moveArray[0].equals("use")) {
                if (moveArray[1].equals("adrenaline") && gameMap.getInventory().contains("adrenaline")){
                    player.setLives(player.getLives() +1);
                    gameMap.removeItems(moveArray[1]);
                }
                else {
                    System.out.println("You can't use that item!");
                }
            } else if (move.matches("show menu|menu")) {
                getMenu();
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
                }

                if(input.matches("p|play")){
                    System.out.println("Music on");
                    themeSong.start();
                }

                if(input.matches("s|stop")){
                    System.out.println("Music off");
                    themeSong.stop();

                }

            }
            else {
                System.out.println("Command not recognized");
            }

            trap();
            randomize();
            checkWin();
            if (checkWin) {
                setOver(true);
                break;
            }
        }
    }
}

// OPEN song
// Theme song
// narration & theme song will toggle opposite of each other
    // sound fx for actions and menu selection
// ending and winning song/sound