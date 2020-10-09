package ampifyServer;

import java.io.Serializable;

public class Song implements Serializable {
    String ip,songname,artist,album;
    int likes,listens;
    int task;
    //if task=1 then play song
    // if task =2 then download song
    Song(String ip,String songname,String artist,String album,int likes,int listens,int task)
    {
        this.ip=ip;
        this.songname=songname;
        this.artist=artist;
        this.album=album;
        this.likes=likes;
        this.listens=listens;
        this.task=task;

    }
    public String getAlbum() {
        return album;
    }
    public String getArtist() {
        return artist;
    }
    public int getTask() {
        return task;
    }
    public String getIp() {
        return ip;
    }
    public String getSongname() {
        return songname;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songname='" + songname + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", likes=" + likes +
                ", listens=" + listens +
                '}';
    }
}
