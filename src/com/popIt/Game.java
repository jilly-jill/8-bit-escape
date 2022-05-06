package com.popIt;

import java.io.IOException;
import java.util.*;
import com.popIt.design.*;

import javax.sound.sampled.Clip;
public class Game {
    private SoundPlayer sound = new SoundPlayer();
    private final Player player = new Player();
    private final Scanner scanner = new Scanner(System.in);
    private GameMap gameMap = new GameMap();
    private Ascii ascii = new Ascii();
    private boolean isOver;
    private boolean endGamePlay;
    private boolean checkWin;
    private boolean isBoss;
    private Clip openSound = sound.play("Resources/sound/8-bit-audio.wav", true, 0);
    private boolean beatMiniGame;
    private ArrayList<String> winConditions = new ArrayList<>();


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

    public boolean isBoss() {
        return isBoss;
    }

    public void setBoss(boolean boss) {
        isBoss = boss;
    }

    public boolean beatMiniGame() {
        return beatMiniGame;
    }

    public void setBeatMiniGame(boolean beatMiniGame) {
        this.beatMiniGame = beatMiniGame;
    }


    public ArrayList<String> getWinConditions() {
        return winConditions;
    }

    public void setWinConditions(ArrayList<String> winConditions) {
        this.winConditions = winConditions;
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
//                    System.out.println("game is over= " + isOver());
//                    System.out.println("end game play= " + isEndGamePlay());
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
        openSound.start();
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

    private Boolean miniGameCheck() {
        while (gameMap.getMiniGame().equals(true)) {
            bossFight();
            //check for items -> if in inventory -> retrieve from value CONTAINS  -> remove items used to build CONTAINS
            if (gameMap.getInventory().contains("bottle") && gameMap.getInventory().contains("lighter") && gameMap.getInventory().contains("bandages")) {
                System.out.println("YOU HAVE USED THE BOTTLE, LIGHTER, AND BANDAGES TO CREATE A MOLOTOV COCKTAIL.\nBUILDING REQUIRES 1-TURN.\n BE SURE TO CHECK YOUR INVENTORY ON YOUR NEXT TURN!\n\n> ");
                gameMap.retrieveItems("molotov-cocktail");
                gameMap.removeItems("bottle");
                gameMap.removeItems("lighter");
                gameMap.removeItems("bandages");
                break;
            }
            if (gameMap.getInventory().contains("rusty-shotgun") && gameMap.getInventory().contains("shotgun-shells")) {
                System.out.println("YOU HAVE LOADED THE SHOTGUN SHELLS INTO THE RUSTY SHOTGUN.\n" +
                        "\nYOU ARE LOADING THE SHOTGUN.\n LOADING REQUIRES 1-TURN.\n BE SURE TO CHECK YOUR INVENTORY ON YOUR NEXT TURN!\n\n> ");
                gameMap.retrieveItems("loaded-shotgun");
                gameMap.removeItems("rusty-shotgun");
                gameMap.removeItems("shotgun-shells");
                break;
            } else {
                gameMap.setMiniGame(true);
                return gameMap.getMiniGame();
            }

        }
        return gameMap.getMiniGame();
    }
    private int bossFight() {
        int number = randomize();
        while (gameMap.getMiniGame().equals(true)) {
            if (number == 2) {
                setBoss(true);
                if (gameMap.getInventory().contains("molotov-cocktail")) {
                    gameMap.setCurrentRoom("corridor13");
                    setBoss(false);
                    gameMap.setMiniGame(false);
                    setBeatMiniGame(true);
                    gameMap.removeItems("molotov-cocktail");
                }
                if (gameMap.getInventory().contains("loaded-shotgun")) {
                    ascii.getText("text/both.txt");
                    gameMap.removeItems("loaded-shotgun");
//                } else {
//                        ascii.getText("text/both.txt");
//                        gameMap.removeItems("molotov-cocktail");
//                        gameMap.retrieveItems("token");
//                        gameMap.getRoomDesc();
//                }
                } else {
                    player.setLives(player.getLives() - 1);
                    ascii.getText("text/notnem.txt");
                    setBoss(false);
                }
            }
            return player.getLives();


        }
        return player.getLives();

    }




    /*JS - 05/03 - Randomize function generates number 1-8,
    if number is >= 1 an ascii image of a ghost appears and player lives are set- 1
    player.getLives() is returned with current value
    Code checked, sout remains if you want to check generated digits and verify logic works*/
    private int randomize() {
        double digit = Math.random() * 10;
        System.out.println(digit);
        int number;
        if(digit >= 7){
            number = 2;
        }
        else {
            number = 1;
        }
        return number;
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
        gameMap.setCurrentRoom("corridor11");
        //Clip themeSong = sound.play("Resources/sound/CantinaBand60.wav", true, 0);
        openSound.stop();

        while (true) {
            System.out.println(beatMiniGame());
            clearScreen();
            showStatus();
            miniGameCheck();
//            bossFight();

            String move = "";
            while (move.equals("")) {
                System.out.println("enter command >");
                move = scanner.nextLine().toLowerCase();
            }
            String[] moveArray = move.split(" ");

            if (moveArray[0].equals("go")) {
                if (moveArray[1].equals(gameMap.getMap(moveArray[1])))
                    gameMap.setCurrentRoom(gameMap.getCurrentRoom());
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
                    //themeSong.start();
                }

                if(input.matches("s|stop")){
                    System.out.println("Music off");
                    //themeSong.stop();

                }

            }
            else {
                System.out.println("Command not recognized");
            }

            trap();
            checkWin();
            if (checkWin) {
                setOver(true);
                break;
            }
        }
    }
}


