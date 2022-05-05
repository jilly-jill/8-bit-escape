package com.popIt;

import javax.sound.sampled.*;
import java.io.File;

class SoundPlayer {

    public Clip play(String wavName, int milSec){
        File sound = new File(wavName);
        Clip clip = null;
        try{
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
            Thread.sleep(milSec);
            clip.stop();

        } catch (Exception e){
            e.printStackTrace();
        }
        return clip;
    }

}
