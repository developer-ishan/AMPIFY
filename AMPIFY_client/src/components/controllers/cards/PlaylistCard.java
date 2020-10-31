package components.controllers.cards;

import commonPackages.models.Playlist;
import commonPackages.models.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlaylistCard implements Initializable {
    private Playlist playlist;
    @FXML
    private Label name;

    @FXML
    private Button playBtn;

    @FXML
    private Button openPlaylist;

    @FXML
    void openPlaylistListener(ActionEvent event) {
        System.out.println("Open the playlist");
    }

    @FXML
    void playListener(ActionEvent event) {
        //TODO play the playlist
        System.out.println("Playing the playlist with following songs:");
        ArrayList<Song> songs = playlist.getSongs();
        songs.forEach(song -> {
            System.out.println(song);
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(playlist.getName());
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
