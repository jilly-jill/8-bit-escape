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

TODO: record voice for narration
TODO: implement flashlight function w/ NPC interaction
TODO: add child drawing to items in zombies -> library -- if player picks up drawing unlock bonus/ascii art

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
    private boolean hasFlashlight;
    private boolean isBoss;
//    private Clip openSound = sound.play("Resources/sound/opening_narration.wav", true, 0);
    private boolean beatMiniGame = false;



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

    public boolean isHasFlashlight() {
        return hasFlashlight;
    }

    public void setHasFlashlight(boolean hasFlashlight) {
        this.hasFlashlight = hasFlashlight;
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
        //openSound.start();
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
        gameMap.roomInfo();
        System.out.println("\n ========== " + player.getUsername() + "'s Current Status ==========\n" +
                "  Current Room: " + gameMap.getCurrentRoom() + "\n" +
                "  Current Lives: " + player.getLives() + "\n" +
                "  Current Inventory: " + gameMap.getInventory() + "\n" +
                "========================================\n");

    }
    /* JS - if player has token and is in maze center -> win
    if player has 0 lives remaining -> lose
    else set checkWin remains false
    Called In: gamePlay()
     */
    private boolean checkWin() {
        String currentRoom = gameMap.getCurrentRoom();
        if (currentRoom.equals("mazecenter") && gameMap.getInventory().contains("token")) {
            checkWin = true;
            ascii.getText("text/win.txt");
            ;
        } else if (player.getLives() < 1) {
            checkWin = true;
            ascii.getText("text/lose.txt");
            ;
        } else {
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

    /*JS - Checks inventory of player
       If inventory has items that can be combined, they are combined and individual elements removed
       Called In: bossFight()
     */
    private void miniGameCheck() {
        //check for items -> if in inventory -> retrieve from value CONTAINS  -> remove items used to build CONTAINS
        if (gameMap.getInventory().contains("bottle") && gameMap.getInventory().contains("lighter") && gameMap.getInventory().contains("bandages")) {
            System.out.println("YOU HAVE USED THE BOTTLE, LIGHTER, AND BANDAGES TO CREATE A MOLOTOV COCKTAIL.\nBUILDING REQUIRES 1-TURN.\n BE SURE TO CHECK YOUR INVENTORY ON YOUR NEXT TURN!\n\n> ");
            gameMap.retrieveItems("molotov-cocktail");
            gameMap.removeItems("bottle");
            gameMap.removeItems("lighter");
            gameMap.removeItems("bandages");
        }
        if (gameMap.getInventory().contains("rusty-shotgun") && gameMap.getInventory().contains("shotgun-shells")) {
            System.out.println("YOU HAVE LOADED THE SHOTGUN SHELLS INTO THE RUSTY SHOTGUN.\n" +
                    "\nYOU ARE LOADING THE SHOTGUN.\n LOADING REQUIRES 1-TURN.\n BE SURE TO CHECK YOUR INVENTORY ON YOUR NEXT TURN!\n\n> ");
            gameMap.retrieveItems("loaded-shotgun");
            gameMap.removeItems("rusty-shotgun");
            gameMap.removeItems("shotgun-shells");
        }
    }

    /*JS - Core mini-game boss fight logic
        completed 05/07
        Called in: gamePlay()
     */
    private int bossFight() {
        int number = randomize();
        //while gamemap -> zombies.json
        while (gameMap.getMiniGame().equals(true)) {
            miniGameCheck();
            if (gameMap.getInventory().contains("walkie-talkie")){
                System.out.println("play walkie talkie message");
                gameMap.removeItems("walkie-talkie");
            }
            //if randomizer returns 2 -> BOSS BATTLE
            if (number == 2) {
                setBoss(true);
                //COND CHECK: if player has either molotov cocktail or loaded shotgun or BOTH
                if (gameMap.getInventory().contains("molotov-cocktail") || gameMap.getInventory().contains("loaded-shotgun")
                        || gameMap.getInventory().contains("molotov-cocktail") && gameMap.getInventory().contains("loaded-shotgun")) {
                    setBoss(false);
                    //call Check.getZombieItems & iterate over to remove zombie specific items
                    for(String item : Check.getZombieItems()){
                        if(gameMap.getInventory().contains(item)){
                        gameMap.removeItems(item);
                    }}
                    //add token to player inventory so they meet one of the game win conditions
                    if (!gameMap.getInventory().contains("token")){
                        gameMap.retrieveItems("token");
                    }
                    //return player from mini-game to corridor 13 in main map
                    gameMap.setCurrentRoom("corridor13");
                    //return gamemap to main map
                    gameMap.setMiniGame(false);
                    //if player has both items -> both items ascii art -> remove both -> return lives
                    if (gameMap.getInventory().contains("molotov-cocktail") && gameMap.getInventory().contains("loaded-shotgun")) {
                        ascii.getText("text/both.txt");
                        return player.getLives();
                    }
                    //if player has molotov-cocktail -> item ascii art -> remove  -> return lives
                    if (gameMap.getInventory().contains("molotov-cocktail")) {
                        ascii.getText("text/molotov.txt");
                        return player.getLives();
                    }
                    //if player has loaded-shotgun -> item ascii art -> remove  -> return lives
                    if (gameMap.getInventory().contains("loaded-shotgun")) {
                        ascii.getText("text/shotgun.txt");
                        return player.getLives();
                    }
                } else {
                    //player loses a life -> attacked by notnemesis -> boss battle over -> still in minigame
                    player.setLives(player.getLives() - 1);
                    ascii.getText("text/notnem.txt");
                    setBoss(false);
                    return player.getLives();
                }
            }
            return player.getLives();

        }
        return player.getLives();
        }

    //JS - plays message when walkie-talkie is picked up, detailing instructions -
    // dropped once message is complete.
    private void walkieTalkie() {
        Clip walkieTalkieMessage = sound.play("sound/walkie_talkie.wav", true, 0);
        ArrayList<String> resultArray = gameMap.getInventory();
        if (resultArray.contains("walkie-talkie")) {
            walkieTalkieMessage.start();
            System.out.println("WALKIE TALKIE PLAYS");
            gameMap.removeItems("walkie-talkie");
        }
    }

    /*JS - 05/03 - random number 1-10, if 8+ int 2 returned else int 1
    Called In: bossFight()
     */
    private int randomize() {
        double digit = Math.random() * 10;
        System.out.println(digit);
        int number;
        if (digit >= 5) {
            number = 2;
        } else {
            number = 1;
        }
        return number;
    }
    /*JS - 05/03 - if the current room contains "trap" - ascii.ghost generates ghost image and player loses 1 life
     CALLED IN: gamePlay()
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
    /* setHasFlashlight -> true ->
    run randomize if > 5
    NPC acts riddle -> player can answer or walk away -> if true receive flashlight
    & set hasflashlight to false
     */

    private ArrayList<String> riddle() {
        ArrayList<String> resultArray = gameMap.getInventory();
        setHasFlashlight(true);
        if (gameMap.getIsNpc() == true) {
            int num = randomize();
            if (num == 2 && !resultArray.contains("flashlight")) {
                System.out.println("A Sphinx emerges from the shadows\n\nI have a question for you traveller\n" +
                        "If you answer correctly, I may have something that can assist you. Would you like to play? [Y] or [N]");
                String input = scanner.nextLine();
                if (input.matches("Y|y|yes")) {
                    System.out.println("What room do ghosts avoid?");
                    String answer = scanner.nextLine();
                    if (answer.matches("living room|livingroom|living-room")) {
                        if(!resultArray.contains("flashlight")) {
                            System.out.println("CORRECT! This flashlight may help you on your path.");
                            resultArray.add("flashlight");
                        } else {
                            System.out.println("Too bad\nThe Sphinx disappears");
                            setHasFlashlight(false);
                        }
                        return resultArray;
                    } else {
                        System.out.println("'Safe travels.'\nThe Sphinx disappears.");
                        setHasFlashlight(false);
                        return resultArray;
                    }
                }
            }
        }
        return resultArray;
    }

    private void gamePlay() {
        player.setLives(5);
        Check.zombieList();

        gameMap.setCurrentRoom("corridor11");
        //Clip themeSong = sound.play("Resources/sound/CantinaBand60.wav", true, 0);
        //  openSound.stop();

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
                if (moveArray[1].equals("adrenaline") && gameMap.getInventory().contains("adrenaline")) {
                    player.setLives(player.getLives() + 1);
                    gameMap.removeItems(moveArray[1]);
                } else {
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

                if (input.matches("p|play")) {
                    System.out.println("Music on");
                    //themeSong.start();
                }

                if (input.matches("s|stop")) {
                    System.out.println("Music off");
                    //themeSong.stop();
                }
            } else {
                System.out.println("Command not recognized");
            }
            riddle();
            trap();
            bossFight();
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

