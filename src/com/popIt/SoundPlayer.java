package com.popIt;

import javax.sound.sampled.*;
import java.io.File;

class SoundPlayer {

    public Clip play(String wavName, Boolean playAll, int milSec){
        File sound = new File(wavName);
        Clip clip = null;

        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();

            if(!playAll){
                Thread.sleep(milSec);
                clip.stop();
            }

        } catch (Exception e){
            System.out.println("Can't find file to play sound");
            e.printStackTrace();
        }
        return clip;
    }

}