package components.controllers;

import commonPackages.models.Playlist;
import commonPackages.models.Song;
import commonPackages.requests.Request;
import commonPackages.requests.playlist.AddSong;
import commonPackages.requests.song.ListSongs;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.playlist.AddSongResponse;
import commonPackages.responses.song.ListSongsResponse;
import components.controllers.cards.SongCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import socket.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlaylistScene implements Initializable {

    private Playlist playlist;
    private Client client;
    private String selectedSong;
    private Alert errAlert,confirmAlert;

    public PlaylistScene(Playlist playlist,Client client){
        this.playlist = playlist;
        this.client = client;
        this.selectedSong = "";
        errAlert = new Alert(Alert.AlertType.ERROR);
        confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    }

    @FXML
    private Label name;

    @FXML
    private Button play;

    @FXML
    private FlowPane songsList;

    @FXML
    private MenuButton songMenu;

    @FXML
    void playListener(ActionEvent event) {
        System.out.println("Playing the playlist.");
    }

    @FXML
    void addSongListener(ActionEvent event){
        if(selectedSong.length()<1){
            errAlert.setHeaderText("Invalid Input");
            errAlert.setContentText("No song selected");
            errAlert.show();
        } else{
            Request req = new AddSong(client.getToken(),selectedSong,playlist.getId());
            client.sendRequest(req);
            AddSongResponse res = (AddSongResponse) client.getResponse();
            if(res.getCode() != ResponseCode.SUCCESS){
                errAlert.setHeaderText(res.getCode().toString());
                errAlert.setContentText(res.getMessage());
                errAlert.show();
            } else{
                Song song = res.getSong();
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
                confirmAlert.setHeaderText("New Song Added Successfully.");
                confirmAlert.show();
            }
        }
        selectedSong="";
        songMenu.setText("Select Song");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.setText(playlist.getName());
        setSongs(playlist.getSongs());

        Request req = new ListSongs(client.getToken());
        client.sendRequest(req);
        ListSongsResponse res = (ListSongsResponse) client.getResponse();
        System.out.println("resp received");
        ArrayList<Song> songArrayList = res.getSongs();
        songArrayList.forEach(song -> {
            //TODO if song not in the all list song
            MenuItem item = new MenuItem();
            item.setId(song.getId());
            item.setText(song.getName());
            item.setOnAction(selectEvent);
            songMenu.getItems().add(item);
        });
    }

    // create action event
    EventHandler<ActionEvent> selectEvent = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            MenuItem item = (MenuItem)e.getSource();
            selectedSong = item.getId();
            songMenu.setText(item.getText());
        }
    };

    public void setSongs(ArrayList<Song> songs){
        songs.forEach((song)->{
            System.out.println(song);
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

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

