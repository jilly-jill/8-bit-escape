package com.popIt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class input {

    private static String currentRoom;


    public static String getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(String currentRoom) {
        input.currentRoom = currentRoom;
    }

    public static void main(String[] args) {
        System.out.println("Input a command (verb) : \n ");
        Scanner playerInput = new Scanner(System.in);
        String input = playerInput.nextLine();
        input = input.toLowerCase();
        String[] inputList = input.split(" ");

        List<String> inventory = new ArrayList<>();

        // Need to add more conditionals the more actions that we add (go, get, use)
        // Keywords: go(N,S,E,W) , get(all items), use(weapon, shell, antidote, tablet), engage(with npc)


        if (inputList[0].matches("go|move|walk")) {
            // if inputList[1] is need 4 directions: n|north, s|south, e|east, w|west
            // need to check if it is a valid location within the JSON object as well.
            // After we verify it is a valid location, need to make
            if (inputList[1].matches("n|north|up")) {
                // this is where we will do something
                System.out.println("we are going to " + inputList[0] + " the " + inputList[1] + "(north)");
            } else if (inputList[1].matches("s|south|down")) {
                // this is where we will do something
                System.out.println("we are going to " + inputList[0] + " the " + inputList[1] + "(south)");
            } else if (inputList[1].matches("e|east|right")) {
                // this is where we will do something
                System.out.println("we are going to " + inputList[0] + " the " + inputList[1] + "(east)");
            } else if (inputList[1].matches("w|west|left")) {
                // this is where we will do something
                System.out.println("we are going to " + inputList[0] + " the " + inputList[1] + "(west)");
            } else {
                System.out.println("You need to choose a valid direction [n]orth, [s]outh, [e]ast, [w]est");
            }
        }
        //turn to function -> pass in inventory return updated inventory
        // the logic for when we get one of the items
        else if (inputList[0].matches("get|grab|take")) {

            // if inputList[1] is in the items object in current room, then move to the inventory global value
            if (inputList[1].matches("walkie talkie") && getCurrentRoom().equals("hall")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);
            }
            else if (inputList[1].matches("alcohol|bottle|rum") && getCurrentRoom().equals("kitchen")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);
            }
            else if (inputList[1].matches("shotgun|gun") && getCurrentRoom().equals("basement")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);
            }
            else if (inputList[1].matches("shotgun shell|shell") && getCurrentRoom().equals("closet")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);
            }
            else if (inputList[1].matches("lighter") && getCurrentRoom().equals("closet")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);
            }
            else if (inputList[1].matches("adrenaline") && getCurrentRoom().equals("infirmary")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);
            }
            else if (inputList[1].matches("bandages") && getCurrentRoom().equals("infirmary")) {
                inventory.add(inputList[1]);
                System.out.println(inventory);

            }
            else {
                System.out.println("You cannot get this item!");
            }
        }
        // the if statements for using an item if it's in your inventory
        else if (inputList[0].matches("use")) {
            if(inventory.contains(inputList[1])) {
                // this is where we do the associated use with the item
                System.out.println("Retrieve the action item from the object and sout it");
            }
        }

        // the logic for when we type we want to engage with someone
        // Need valid list of all NPCs (zombie?)
        else if (inputList[0].matches("engage")) {
            // if the NPC name is in the current room?
            if (inputList[1].matches("valid npc") && getCurrentRoom().equals("NPC Room")) {
                // this is where we will do something with the npc
                // associated engage action with npc
            }
            System.out.println("we are going to " + inputList[0] + " the " + inputList[1] + "(engage)");
        }
        else if (inputList[0].matches("restart")) {
            System.out.println("This is where we restart the game");
        }
        else if (inputList[0].matches("quit")) {
            System.out.println("This is where we quit the game");
        }
        else {
            System.out.println("You need to use a proper command [go, get, use, engage, help]");
        }
    }
}