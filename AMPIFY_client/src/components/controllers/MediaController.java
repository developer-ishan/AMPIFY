package components.controllers;

import commonPackages.DoublyLinkedList;
import commonPackages.SubtitleDs;
import commonPackages.models.Playlist;
import commonPackages.models.Song;
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
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


public class MediaController{

    private Media m;
    public MediaPlayer player;
    private int repeatTask=0;
    @FXML
    private Slider progressBar=new Slider();
    @FXML
    private Text mediaDuration=new Text();
    @FXML
    private Text mediaplayed=new Text();
    @FXML
    private Text songtitle=new Text();
    @FXML
    private Text subtitle=new Text();
    @FXML
    private Button repeat;
    @FXML
    private Slider volumeSlider=new Slider();
    public MediaController() throws UnknownHostException, MalformedURLException {
    }
    private DoublyLinkedList<Song> linkedList =new DoublyLinkedList<>();
    DoublyLinkedList.Node head= linkedList.head;

    private SubtitleDs<String> Subtitles=new SubtitleDs<>();

    public MediaController(Song song) {
        try {
            URL url=new URL("http://localhost:8081/songs/"+song.getId()+".mp3");
            m=new Media(url.toString());
            linkedList.add(song);
            player=new MediaPlayer(m);
            player.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Requiredmethods();
    }
    public MediaController(Playlist playlist){
    List<Song> list=playlist.getSongs();
        ListIterator<Song> iterator= list.listIterator();
        while(iterator.hasNext()){
            linkedList.add(iterator.next());
        }
        URL url= null;
        try {
            url = new URL("http://localhost:8081/songs/"+linkedList.head.file.getId()+".mp3");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        m=new Media(url.toString());
        player=new MediaPlayer(m);
        player.play();
        Requiredmethods();
    }
    public MediaController(Media media){
        this.m=media;
        player=new MediaPlayer(media);
        player.play();
        player.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                songtitle.setText("");
                subtitle.setText("");
            }
        });
        Requiredmethods();
    }
    private void Requiredmethods() {
        if(linkedList.head.file!=null) {
            System.out.println(linkedList.head.file.toString()+"\n");
            URL SrtURL= null;
            try {
                SrtURL = new URL("http://localhost:8081/lyrics/"+linkedList.head.file.getId()+".srt");
            BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(SrtURL.openStream(), "UTF-8"));
                while (true)
                {
                    String lineNum,time,subtitle,nullline;
                    lineNum=reader.readLine();
                    if(lineNum==null){
                        System.out.println("Srt is finished");
                        break;
                    }
                    time=reader.readLine();
                    subtitle=reader.readLine();
                    nullline=reader.readLine();
                    while(true){
                        if(nullline.equals("")){
                            break;
                        }
                        else
                        {
                            subtitle=subtitle.concat("\n"+nullline);
                        }
                        nullline=reader.readLine();
                    }
                    String timestamp[]=time.split("-->");
                    timestamp[0]=timestamp[0].split(",")[0];
                    timestamp[1]=timestamp[1].split(",")[0];
                    Subtitles.add(subtitle,timestamp[0],timestamp[1]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (UnsupportedEncodingException | FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue,Duration newValue) {
                    int sec=(int)newValue.toSeconds()%60;
                    int min=(int)newValue.toMinutes();
                    int hrs= (int) newValue.toHours();
                    songtitle.setText(linkedList.head.file.getName());
                    String timestamp;
                    if(sec>2){
                        sec=sec-2;
                    }
                    if(sec>=0&&sec<10){
                       timestamp="0"+hrs+":0"+min+":0"+sec;
                    }
                    else{
                        timestamp="0"+hrs+":0"+min+":"+sec;
                    }
                    subtitle.setText(Subtitles.search(timestamp));
                }
            });
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
                int sec=((int)totalDuration.toSeconds())%60;
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
        Requiredmethods();
        player.play();
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
        if(linkedList.head.file!=null){
            if (repeatTask == 0) {
                repeat.setText("Repeat");
                player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        next(event);
                    }
                });
            } else if (repeatTask == 1) {
                repeat.setText("Repeat Track");
                player.setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        player.seek(Duration.ZERO);
                    }
                });
            } else {
                repeat.setText("Don't Repeat");
                if (head == linkedList.head.next) {
                    player.setOnEndOfMedia(new Runnable() {
                        @Override
                        public void run() {
                            player.seek(Duration.ZERO);
                            player.stop();
                        }
                    });
                }
            }
            repeatTask = (++repeatTask) % 3;
        }
        else{
            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    player.seek(Duration.ZERO);
                }
            });
        }
    }
    @FXML
    void next(ActionEvent event)
    {
        player.stop();
        if(linkedList.head!=null&&linkedList.head.file!=null){
            linkedList.head = linkedList.head.next;
            URL url= null;
            try {
                url = new URL("http://localhost:8081/songs/"+linkedList.head.file.getId()+".mp3");
                m=new Media(url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        player = new MediaPlayer(m);
        player.play();
        Requiredmethods();
    }
    @FXML
    void previous(ActionEvent event)
    {
        player.stop();
        if(linkedList.head!=null){
            linkedList.head = linkedList.head.previous;
            URL url= null;
            try {
                url = new URL("http://localhost:8081/songs/"+linkedList.head.file.getId()+".mp3");
                m=new Media(url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        player = new MediaPlayer(m);
        player.play();
        Requiredmethods();
    }
    @FXML
    void shuffle() {
        if(linkedList.head.file!=null){
            player.stop();
            Random rand = new Random();
            int n = rand.nextInt() % 5 + 1;
            while (n-- != 0) {
                linkedList.head = linkedList.head.next;
            }
            URL url = null;
            try {
                url = new URL("http://localhost:8081/songs/" + linkedList.head.file.getId() + ".mp3");
                m = new Media(url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            player = new MediaPlayer(m);
            player.play();
            Requiredmethods();
        }
    }
}
