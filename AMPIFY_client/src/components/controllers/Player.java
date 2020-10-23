package components.controllers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class Player extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        //Initialising path of the media file, replace this with your file path
        String path = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";

        //Instantiating Media class
//        Media media = new Media(
//          path
//        );
        Media media = new Media(new File("/home/ishan/Desktop/javaTCP/web/song.mp3").toURI().toString());
        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played
        mediaPlayer.setAutoPlay(true);
        stage.setTitle("Playing Audio");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
