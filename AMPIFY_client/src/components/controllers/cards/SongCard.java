package components.controllers.cards;

import commonPackages.DownloadFile;
import commonPackages.models.Song;
import components.controllers.MediaController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SongCard implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Label name;
    @FXML
    private Label duration;
    @FXML
    private Button play;
    @FXML
    private Button download;
    @FXML
    private Button like;
    boolean liked=false;
    private Song song;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void download(ActionEvent event){
        try {
            DownloadFile.download(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void play(ActionEvent event) {

        Stage stage = new Stage();
        stage.setWidth(800);
        stage.setHeight(600);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mediaplayer.fxml"));
        MediaController mediaController = new MediaController(song);
        loader.setController(mediaController);
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(song.getName());
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    mediaController.player.stop();
                }
            });
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("http://localhost:8081/songs_pics/" + song.getId() + ".jpg", true);
        imageView.setImage(image);
        name.setText(song.getName());
        duration.setText(String.valueOf(song.getDuration()));
        play.setOnAction(event -> {
            play(event);
        });
        download.setOnAction(event ->
        {
            download(event);
        });
        like.setOnAction(event -> {
            if(liked==false){
                liked=true;
                like.setText("Liked");
                like.setStyle("-fx-background-color: #fc4949;");
            }
            else{
                liked=false;
                like.setText("Like");
                like.setStyle("-fx-background-color: #c7c3c3;");
            }
        });
    }

}
