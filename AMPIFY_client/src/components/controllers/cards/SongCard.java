package components.controllers.cards;

import commonPackages.models.Song;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SongCard implements Initializable {
    @FXML
    private ImageView imageView;

    @FXML
    private Label name;

    @FXML
    private Label duration;

    private Song song;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image("http://localhost:8080/songs_pics/"+song.getId()+".jpg",true);
        imageView.setImage(image);
        name.setText(song.getName());
        duration.setText(String.valueOf(song.getDuration()));
    }
}
