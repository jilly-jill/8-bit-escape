package com.popIt;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;


class SoundPlayer {

private ReadFile readFile = new ReadFile();

    public Clip play(String wavName, Boolean playAll, int playLengthInMilSec){
        InputStream sound = new BufferedInputStream(readFile.getFileFromResourceAsStream(wavName, GameMap.class));
        Clip clip = null;

        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();

            if(!playAll){
                Thread.sleep(playLengthInMilSec);
                clip.stop();
            }

        } catch (Exception e){
            System.out.println("Can't find file to play sound");
            e.printStackTrace();
        }
        return clip;
    }

}
