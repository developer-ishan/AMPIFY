package components.controllers.cards;

import commonPackages.models.Playlist;
import commonPackages.models.Song;
import components.controllers.MediaController;
import components.controllers.PlaylistScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import socket.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlaylistCard implements Initializable {
    private Playlist playlist;
    private Client client;
    @FXML
    private Label name;

    @FXML
    private Button playBtn;

    @FXML
    private Button openPlaylist;

    @FXML
    void openPlaylistListener(ActionEvent event) {
        System.out.println("Open the playlist");
        Stage stage = new Stage();
        stage.setWidth(800);
        stage.setHeight(600);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/playlist.fxml"));
        PlaylistScene playlistScene = new PlaylistScene(playlist,client);
        loader.setController(playlistScene);
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void playListener(ActionEvent event) {
        //TODO play the playlist
        System.out.println("Playing the playlist with following songs:");
        ArrayList<Song> songs = playlist.getSongs();
        System.out.println("Playing the playlist.");
        Stage stage = new Stage();
        stage.setWidth(800);
        stage.setHeight(600);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mediaplayer.fxml"));
        MediaController mediaController = new MediaController(playlist);
        loader.setController(mediaController);
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(playlist.getName());
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
        name.setText(playlist.getName());
//        playBtn.setOnAction(event -> {
//            playListener(event);
//        });
//        openPlaylist.setOnAction(event -> {
//            openPlaylistListener(event);
//        });
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}
