package ampifyServer;

import java.io.Serializable;

public class Song implements Serializable {
    String ip,songname,artist,album;
    int likes,listens;
    Song(String ip,String songname,String artist,String album,int likes,int listens)
    {
        this.ip=ip;
        this.songname=songname;
        this.artist=artist;
        this.album=album;
        this.likes=likes;
        this.listens=listens;
    }
    public String getAlbum() {
        return album;
    }
    public String getArtist() {
        return artist;
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
