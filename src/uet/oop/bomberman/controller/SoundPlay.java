package uet.oop.bomberman.controller;

import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class SoundPlay extends JFrame {

    public int flag = 0;
    private Clip clip;
    String soundFile;
    SoundPlay(String soundFile) {
        this.soundFile = soundFile;
        try{
            File f = new File("./" + soundFile);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
    public void play(){
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();
    }
}