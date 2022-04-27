package com.popIt;

class Player {
     //fields
     private String username;
     private int lives; //player starts with 3 lives
    // inventory list for artifact
    // inventory list for items
 
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
}
