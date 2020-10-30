package components.controllers.user;

import commonPackages.models.Song;
import commonPackages.models.User;
import commonPackages.requests.Request;
import commonPackages.requests.song.ListSongs;
import commonPackages.responses.Response;
import commonPackages.responses.song.ListSongsResponse;
import commonPackages.responses.user.ListInvitesResponse;
import components.controllers.MediaController;
import components.controllers.cards.SongCard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import socket.Client;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable {

    private Client client;
    private User user;
    public Button createGroupBtn;

    public void createGroup(ActionEvent actionEvent) throws Exception {
    }

    @FXML
    private Label name;
    @FXML
    private ImageView pic;
    @FXML
    FlowPane songsList;
    public Home(){
        System.out.println("Home constructor is called.");
    }
    public Home(Song song){
        new MediaController(song);
    }
    @FXML
    private Slider progressBar;
    @FXML
    private Text mediaDuration;
    @FXML
    private Text mediaplayed;
    @FXML
    private Button repeat;
    @FXML
    private Slider volumeSlider;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(user.getName());
        Image image = new Image("http://localhost:8081/profile_pics/default.jpg",true);
        pic.setImage(image);
        setSongs();
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSongs(){
        System.out.println(client.getToken());
        Request req = new ListSongs(client.getToken());
        client.sendRequest(req);
        ListSongsResponse res = (ListSongsResponse) client.getResponse();
        System.out.println("resp received");
        ArrayList<Song> songArrayList = res.getSongs();

        songArrayList.forEach((song)->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/songCard.fxml"));
                SongCard songCard = new SongCard();
                songCard.setSong(song);
                loader.setController(songCard);
                Node node = loader.load();
                songsList.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
