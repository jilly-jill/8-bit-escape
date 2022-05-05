package com.popIt;

import javax.sound.sampled.*;
import java.io.File;

class SoundPlayer {

    public void makeSound(String wav){
        File sound = new File(wav);
        // load wav file into clip to start sound
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
