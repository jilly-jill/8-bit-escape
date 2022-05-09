package com.popIt;


import java.util.*;

public class Check {


    public static ArrayList<String> zombieItems = new ArrayList<>();

    public static ArrayList<String> getZombieItems() {
        return zombieItems;
    }

    public void setZombieItems(ArrayList<String> zombieItems) {
        this.zombieItems = zombieItems;
    }


    public static void zombieList() {
        zombieItems.add("adrenaline");
        zombieItems.add("bandages");
        zombieItems.add("shotgun-shells");
        zombieItems.add("lighter");
        zombieItems.add("bottle");
        zombieItems.add("drawing");
        zombieItems.add("rusty-shotgun");
        zombieItems.add("walkie-talkie");
        zombieItems.add("loaded-shotgun");
        zombieItems.add("molotov-cocktail");
    }


}
