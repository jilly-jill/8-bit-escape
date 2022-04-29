package com.popIt;

import java.util.*;

class Player {
    //fields
    private String username;
    private int lives; //player starts with 3 lives
    private List<String> inventory = new ArrayList<>();


    //Accessor Methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLives() {
        return lives;
    }

    public void setLives() {
        this.lives = 3;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }
}



