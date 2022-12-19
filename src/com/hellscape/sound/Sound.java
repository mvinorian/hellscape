package com.hellscape.sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    
    public final int maxType = 3;
    public static final int bgmTitle = 0;
    public static final int bgmBattle = 1;
    public static final int sfxGetHit = 2;

    private Clip clip;
    private URL[] soundURL;

    public Sound() {
        soundURL = new URL[maxType];
        soundURL[bgmTitle] = getClass().getResource("/sound/bgm/title.wav");
        soundURL[bgmBattle] = getClass().getResource("/sound/bgm/battle.wav");
        soundURL[sfxGetHit] = getClass().getResource("/sound/sfx/getHit.wav");
    }

    public void play(int type) {
        this.setFile(type);
        clip.start();
    }

    public void playLoop(int type) {
        if (clip != null) clip.stop();
        this.setFile(type);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    private void setFile(int type) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[type]);
            this.clip = AudioSystem.getClip();
            this.clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
