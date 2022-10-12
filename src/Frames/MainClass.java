package Frames;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MainClass {
    
    FileInputStream FIS;
    BufferedInputStream BIS;
    
    public Player player;
    public int pauseLocation;
    public int sontotalLenght;
    public String fileLocation;
    
    public void Stop() {
        if (player != null) {
            player.close();
            pauseLocation = 0;
            sontotalLenght = 0;
            MP3PlayerGUI.display.setText("");
            fileLocation = "";
        }
    }
    
    public void Pause() {
        if (player != null) {
            try {
                pauseLocation = FIS.available();
                player.close();
                System.out.println(pauseLocation);
                
            } catch (IOException ex) {
            }
        }
    }
    
    public void Resume() {
        if (player != null) {
            try {
                FIS = new FileInputStream(fileLocation);
                BIS = new BufferedInputStream(FIS);
                player = new Player(BIS);
                FIS.skip(sontotalLenght - pauseLocation);
            } catch (FileNotFoundException | JavaLayerException ex) {
            } catch (IOException ex) {
            }
            new Thread() {
                @Override
                public void run() {
                    try {
                        player.play();
                    } catch (JavaLayerException ex) {
                    }
                }
            }.start();
        }
        
    }
    
    public void Play(String path) {
        try {
            FIS = new FileInputStream(path);
            BIS = new BufferedInputStream(FIS);
            player = new Player(BIS);
            sontotalLenght = FIS.available();
            fileLocation = path + "";
            System.out.println(sontotalLenght);
        } catch (FileNotFoundException | JavaLayerException ex) {
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    player.play();
                    player.play(120);
                    if (player.isComplete() && MP3PlayerGUI.count == 1) {
                        Play(fileLocation);
                    } else if (player.isComplete() && MP3PlayerGUI.count == 0) {
                        Play("R:\\Respect.mp3");
                    }
                } catch (JavaLayerException ex) {
                }
                
                System.out.println(sontotalLenght);
            }
        }.start();
    }
}
