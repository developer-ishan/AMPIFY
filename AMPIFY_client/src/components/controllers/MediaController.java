package AMPIFY_client.src.components.controllers;

import AMPIFY_client.src.commonPackages.DoublyLinkedList;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.UnknownHostException;


public class MediaController {
    //Song song=new Song("TokyoGhoul","....", InetAddress.getLocalHost());
    //URL url=new URL("http://localhost:8888"+"/"+ song.getSongname()+".mp3");
    private Media m;
    private MediaPlayer player;
    private int repeatTask=0;
    @FXML
    private final MediaView mediaView=new MediaView();
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
    public MediaController() throws UnknownHostException, MalformedURLException {
    }
    private DoublyLinkedList<Media> playlistLocal =new DoublyLinkedList<>();
    DoublyLinkedList.Node head= playlistLocal.head;
    @FXML
    void openSongFolder(ActionEvent event) throws MalformedURLException {
        DirectoryChooser chooser=new DirectoryChooser();
        File dir=chooser.showDialog(null);
        for (File file: dir.listFiles()){
            if(file.isFile())
            {
                System.out.println(file.getName().toLowerCase());
                String[] str=file.getName().toLowerCase().split("\\.");
                String ext=str[str.length-1];
                System.out.println(ext);
                if(ext.equals("mp3") || ext.equals("wav") || ext.equals("mp4")) {
                    Media media=new Media(file.toURI().toString());
                    playlistLocal.add(media);
                    media=null;
                }
            }
        }
        m=playlistLocal.head.file;
    }
    @FXML
    void openSongMenu(ActionEvent event) throws MalformedURLException {
//        URL url=new URL("http://localhost:8888/D:/audios/punjabi/Jinne_Saah.mp3");
//        Media media=new Media(url.toString());
//        playlistLocal.add(media);
//        URL url1=new URL("http://localhost:8888/D:/audios/punjabi/Teriyan_Deedan_-Dil_Diyan_Gallan-(MP3Tau.Com)_-320KBPS.mp3");
//        media=new Media(url1.toString());
//        playlistLocal.add(media);
//        m=playlistLocal.head.file;
        try {
            FileChooser chooser=new FileChooser();
            File file = chooser.showOpenDialog(null);
                if (file != null) {
                    m = new Media(file.toURI().toString());
                    Requiredmethods();
                    } else {
                        System.out.println("file is null");
                    }
           } catch (Exception e) {
            System.err.println(e);
        }
    }
    private void Requiredmethods() {
        if(playlistLocal.head.file!=null) {
            System.out.println(playlistLocal.head.file.toString()+"\n");
        }
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
             @Override
             public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,Duration newValue) {
                 progressBar.setValue(newValue.toSeconds());

                 //displaying media played in text
                 int sec=(int)newValue.toSeconds()%60;
                 int min=(int)newValue.toMinutes();
                 int hrs= (int) newValue.toHours();
                 mediaplayed.setText(hrs+":"+min+":"+sec);
             }
        });
        //setting up progress bar to forward or backword the media when clicked on slider
        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        //setting up above function when dragged
        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });
        //displaying total media duration
        player.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration totalDuration) {
                int sec=(int)totalDuration.toSeconds()%60;
                int min=(int)totalDuration.toMinutes();
                int hrs= (int) totalDuration.toHours();
                mediaDuration.setText(hrs+":"+min+":"+sec);
            }
        });
        //implementing volume controll
        volumeSlider.setValue(player.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                player.setVolume(volumeSlider.getValue()/100);
            }
        });
       player.setOnReady(new Runnable() {
           @Override
           public void run() {
               // adjusting slider according to media length
               progressBar.maxProperty().bind(Bindings.createDoubleBinding(
                       () -> player.getTotalDuration().toSeconds(),
                       player.totalDurationProperty()));
           }
       });

    }

    @FXML
    void play(ActionEvent event) throws MalformedURLException {
        player=new MediaPlayer(m);
        Requiredmethods();
        player.play();
        replay(event);
    }
    @FXML
    void pause(ActionEvent event){
        player.pause();
    }
    @FXML
    void forward(ActionEvent event)
    {   // skip 10 sec forward
        player.seek(player.getCurrentTime().add(Duration.seconds(10)));
    }
    @FXML
    void backward(ActionEvent event)
    {   //skip 10 sec backward
        player.seek(player.getCurrentTime().subtract(Duration.seconds(10)));
    }
    @FXML
    void replay(ActionEvent event)
    {
        if(repeatTask==0) {
            repeat.setText("Repeat");
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    next(event);
                }
            });
        }
        else if(repeatTask==1){
            repeat.setText("Repeat Track");
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
        }
        else
        {
            repeat.setText("Don't Repeat");
            if(head== playlistLocal.head.next)
            {
                player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        player.seek(Duration.ZERO);
                        player.stop();
                    }
                });
            }
        }
        repeatTask=(++repeatTask)%3;
    }
    @FXML
    void next(ActionEvent event)
    {
        player.stop();
        playlistLocal.head= playlistLocal.head.next;
        m=playlistLocal.head.file;
        player=new MediaPlayer(m);
        player.play();
        Requiredmethods();
    }
    @FXML
    void previous(ActionEvent event)
    {
        player.stop();
        playlistLocal.head= playlistLocal.head.previous;
        m=playlistLocal.head.file;
        player=new MediaPlayer(m);
        player.play();
        Requiredmethods();
    }
}
