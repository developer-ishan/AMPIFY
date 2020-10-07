package Networking;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;

public class HandleSong implements Runnable{
    @Override
    public void run() {
        playsong();
    }
    private static void playsong() {
        System.out.println("Client: reading from 127.0.0.1:5555");
        try (Socket socket = new Socket("127.0.0.1", 5555)) {
            if (socket.isConnected()) {
                InputStream inputStream = new BufferedInputStream(socket.getInputStream());
                HandleSong.play(inputStream);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static synchronized void play(final InputStream inputStream) throws Exception {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            }
            try {
                clip.open(audioInputStream);
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            clip.start();
            Thread.sleep(100); // given clip.drain a chance to start
            clip.drain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
