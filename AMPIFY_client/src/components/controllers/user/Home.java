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
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import socket.Client;

import java.io.*;
import java.net.MalformedURLException;
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
    public void opensongMenu(ActionEvent event){
        FileChooser chooser=new FileChooser();
        File file=chooser.showOpenDialog(null);
            if(file.isFile())
            {
                System.out.println(file.getName().toLowerCase());
                String[] str=file.getName().toLowerCase().split("\\.");
                String ext=str[str.length-1];
                System.out.println(ext);
                if(ext.equals("mp3") || ext.equals("wav") || ext.equals("mp4")) {
                    Media media=new Media(file.toURI().toString());
                    Stage stage = new Stage();
                    stage.setWidth(800);
                    stage.setHeight(600);

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mediaplayer.fxml"));
                    MediaController mediaController = new MediaController(media);
                    loader.setController(mediaController);
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
            }
        }
}
