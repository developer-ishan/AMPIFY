package components.controllers.user;

import commonPackages.models.Group;
import commonPackages.models.Playlist;
import commonPackages.models.Song;
import commonPackages.models.User;
import commonPackages.requests.GetNotifications;
import commonPackages.requests.Request;
import commonPackages.requests.group.CreateGroup;
import commonPackages.requests.playlist.CreatePlaylist;
import commonPackages.requests.playlist.ListPlaylists;
import commonPackages.requests.song.ListSearch;
import commonPackages.requests.song.ListSongs;
import commonPackages.requests.song.SearchFilterCode;
import commonPackages.requests.user.ListGroups;
import commonPackages.requests.user.ListInvites;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.group.CreateGroupResponse;
import commonPackages.responses.playlist.CreatePlaylistResponse;
import commonPackages.responses.playlist.ListPlaylistsResponse;
import commonPackages.responses.song.ListSongsResponse;
import commonPackages.responses.user.ListGroupsResponse;
import commonPackages.responses.user.ListInvitesResponse;
import components.controllers.MediaController;
import components.controllers.cards.GroupCard;
import components.controllers.cards.PlaylistCard;
import components.controllers.cards.SongCard;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import socket.Client;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Home implements Initializable {

    private Client client;
    private User user;
    private String filterType;
    @FXML
    private ImageView pic;

    @FXML
    private Label name;

    @FXML
    private Button createGroupBtn;

    @FXML
    private VBox groupsList;

    @FXML
    private TextField groupName;

    @FXML
    private Button createGroup;

    @FXML
    private FlowPane songsList;

    @FXML
    private VBox playlistList;

    @FXML
    private VBox sharedPlaylists;

    @FXML
    private VBox publicPlaylists;

    @FXML
    private TextField playlistName;

    @FXML
    private Button createPlaylist;

    @FXML
    private VBox invitesList;

    @FXML
    private ToggleButton filterByName;

    @FXML
    private ToggleGroup filter;

    @FXML
    private ToggleButton filterByGenre;

    @FXML
    private TextField query;

    @FXML
    void createGroupListener(ActionEvent event) {
        String name = groupName.getText();
        if(name.length() < 1){
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid");
            errAlert.setContentText("Group name cannot be empty.");
            errAlert.show();
        } else {
            System.out.println("Creating a new group : "+name);
            Request req = new CreateGroup(client.getToken(),name);
            client.sendRequest(req);
            CreateGroupResponse res = (CreateGroupResponse) client.getResponse();
            if(res.getCode()!= ResponseCode.SUCCESS){
                Alert errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setHeaderText("Invalid");
                errAlert.setContentText(res.getMessage());
                errAlert.show();
            } else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Success");
                alert.setContentText(res.getMessage());
                alert.show();
                this.groupName.setText(null);
                try {
                    Group group = res.getGroup();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/groupCard.fxml"));
                    GroupCard groupCard = new GroupCard();
                    groupCard.setGroup(group);
                    loader.setController(groupCard);
                    Node node = loader.load();
                    groupsList.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void createPlaylistListener(ActionEvent event) {
        String name = playlistName.getText();
        if(name.length() < 1){
            Alert errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid");
            errAlert.setContentText("Group name cannot be empty.");
            errAlert.show();
        } else {
            System.out.println("Creating a new playlist : "+name);
            Request req = new CreatePlaylist(client.getToken(),name);
            client.sendRequest(req);
            CreatePlaylistResponse res = (CreatePlaylistResponse) client.getResponse();
            if(res.getCode()!= ResponseCode.SUCCESS){
                Alert errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setHeaderText("Invalid");
                errAlert.setContentText(res.getMessage());
                errAlert.show();
            } else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Success");
                alert.setContentText(res.getMessage());
                alert.show();
                this.playlistName.setText(null);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/playlistCard.fxml"));
                    PlaylistCard playlistCard = new PlaylistCard();
                    playlistCard.setPlaylist(res.getPlaylist());
                    playlistCard.setClient(client);
                    loader.setController(playlistCard);
                    Node node = loader.load();
                    playlistList.getChildren().add(node);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Home(){
        System.out.println("Home constructor is called.");
    }
    public Home(Song song){
        new MediaController(song);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(user.getName());
        Image image = new Image("http://localhost:8081/profile_pics/default.jpg",true);
        pic.setImage(image);

        //setting up the songs
        Request req = new ListSongs(client.getToken());
        client.sendRequest(req);
        ListSongsResponse res = (ListSongsResponse) client.getResponse();
        System.out.println("resp received");
        ArrayList<Song> songArrayList = res.getSongs();
        setSongs(songArrayList);

        setFilters();
        setGroups();
        setPlaylists();

//        Platform.runLater(new Notifications(client,user.getId(),invitesList,groupsList));
    }
    public void setGroups() {
        Request req = new ListGroups(client.getToken());
        client.sendRequest(req);
        ListGroupsResponse res = (ListGroupsResponse) client.getResponse();
        System.out.println(res);
        ArrayList<Group> groups = res.getGroups();
        groups.forEach(group -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/groupCard.fxml"));
                GroupCard groupCard = new GroupCard();
                groupCard.setGroup(group);
                loader.setController(groupCard);
                Node node = loader.load();
                groupsList.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

    public void setSongs(ArrayList<Song> songArrayList){
        songsList.getChildren().removeAll(songsList.getChildren());
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
    public void opensongMenu(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        if (file.isFile()) {
            System.out.println(file.getName().toLowerCase());
            String[] str = file.getName().toLowerCase().split("\\.");
            String ext = str[str.length - 1];
            System.out.println(ext);
            if (ext.equals("mp3") || ext.equals("wav") || ext.equals("mp4")) {
                Media media = new Media(file.toURI().toString());
                Stage stage = new Stage();
                stage.setWidth(800);
                stage.setHeight(600);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mediaplayer.fxml"));
                MediaController mediaController = new MediaController(media);
                loader.setController(mediaController);
                stage.setTitle(file.getName());
                Parent root = null;
                try {
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
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
        }
    }
    public void setPlaylists () {
        System.out.println("Setting the user playlists.");
        Request req = new ListPlaylists(client.getToken());
        client.sendRequest(req);
        ListPlaylistsResponse res = (ListPlaylistsResponse) client.getResponse();
        System.out.println(res);
        ArrayList<Playlist> playlists = res.getPlaylists();
        playlists.forEach(playlist -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/playlistCard.fxml"));
                PlaylistCard playlistCard = new PlaylistCard();
                playlistCard.setPlaylist(playlist);
                playlistCard.setClient(client);
                loader.setController(playlistCard);
                Node node = loader.load();
                int role = playlist.getRole();
                int type = playlist.getType();
                if (type == 2) {
                    publicPlaylists.getChildren().add(node);
                } else if (role == 1)
                    playlistList.getChildren().add(node);
                else if (role == 2)
                    sharedPlaylists.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setFilters(){
        filter.selectedToggleProperty().addListener(
                new ChangeListener<Toggle>() {
                    @Override
                    public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle new_toggle) {
                        filterType = ((ToggleButton)new_toggle).getId();
                    }
                }
        );
    }
    @FXML
    void searchListener(ActionEvent event) {
        String queryText = query.getText();

        if(filterType.equals("filterByName")){
            Request req = new ListSearch(client.getToken(),queryText, SearchFilterCode.BY_SONG_NAME);
            client.sendRequest(req);
            ListSongsResponse res = (ListSongsResponse) client.getResponse();
            System.out.println("resp received");
            ArrayList<Song> songArrayList = res.getSongs();
            setSongs(songArrayList);
        } else if(filterType.equals("filterByGenre")){
            Request req = new ListSearch(client.getToken(),queryText, SearchFilterCode.BY_SONG_GENRE);
            client.sendRequest(req);
            ListSongsResponse res = (ListSongsResponse) client.getResponse();
            System.out.println("resp received");
            ArrayList<Song> songArrayList = res.getSongs();
            setSongs(songArrayList);
        } else{
            Request req = new ListSongs(client.getToken());
            client.sendRequest(req);
            ListSongsResponse res = (ListSongsResponse) client.getResponse();
            System.out.println("resp received");
            ArrayList<Song> songArrayList = res.getSongs();
            setSongs(songArrayList);
        }
        query.setText("");
    }
}
