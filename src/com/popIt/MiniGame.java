package com.popIt;

public class MiniGame {
    Player player = new Player();
    Map map = new Map();

    public int playGame() {
        int count = 0;
        while (count < 10 || player.getLives() < 1) {
            bossAppear();
            count++;
            if(player.getLives() < 1){
                map.setMiniGame(false);
                return player.getLives();
            }
            if(count == 10){
                map.setMiniGame(false);
                return player.getLives();
            }
            else {
                System.out.println(count);
            }
        }
       return player.getLives();
    }
    public int bossAppear(){
        System.out.println("TOTALLYNOTNEMISIS APPEARS");
        double rand = (Math.random()*10);
        if (rand >= 8){
            player.setLives(player.getLives()-1);
            System.out.println("BOSS WINS");
            return player.getLives();
        }
        else if(rand > 5 && rand < 7){
            System.out.println("YOU WIN");
        }
        return player.getLives();
    }
}
