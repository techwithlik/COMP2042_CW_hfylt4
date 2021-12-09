package model;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class AudioPlayer {
    public boolean muteAudio;
    private final Clip clip;

    public AudioPlayer(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
                // Load the sound into memory (a clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
            }
            else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

    // Play the sound clip
    }
    public void play(){
        if (!muteAudio){
            clip.setFramePosition(0);  // Resets to the beginning of the clip
            clip.start();
        }
    }

    // Loop the sound clip
    public void loop(){
        if (!muteAudio)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
