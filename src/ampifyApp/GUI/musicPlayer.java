package ampifyApp.GUI;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class musicPlayer implements LineListener {

    Clip audioClip;
    long ClipTime = 0;
    int songNum = 0;
    int size = 0;
    File[] audio_songs = null;

    public void start(File[] files) {
        size = files.length;
        audio_songs = files;
        File audioFile = null;
        if(size > 0) {
            audioFile = files[songNum % size];
        }

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Line.Info info = new Line.Info(Clip.class);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);

            audioClip.open(audioStream);
            audioClip.start();


        } catch(UnsupportedAudioFileException ex) {
            System.out.println("File Format Not Supported!!");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio Line Playback is Unavailble!!");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing audio File!!");
            ex.printStackTrace();
        }
    }

    public void pause() {
        ClipTime = audioClip.getMicrosecondPosition();
        audioClip.stop();
    }

    public void play() {
        audioClip.setMicrosecondPosition(ClipTime);
        audioClip.start();
    }

    public void stop() {
        ClipTime = 0;
        audioClip.stop();
    }

    public void  prev() {
        audioClip.close();
        songNum = songNum - 1;
        ClipTime = 0;
        if(songNum < 0)
            songNum = songNum + size;
        this.start(audio_songs);
    }

    public void next() {
        audioClip.close();
        songNum = (songNum + 1) % size;
        ClipTime = 0;
        this.start(audio_songs);
    }

    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();

        if(type == LineEvent.Type.START) {
            System.out.println("Playback Started");
        }
        else if(type == LineEvent.Type.STOP) {
            System.out.println("Playback Paused!!");
        }
        else if(type == LineEvent.Type.CLOSE) {
            System.out.println("Playback Ended!!");
        }
    }
}
