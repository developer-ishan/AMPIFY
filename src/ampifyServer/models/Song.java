package ampifyServer.models;

import java.io.Serializable;
import java.net.InetAddress;

public class Song implements Serializable {
    String songname,artist;
    InetAddress ip;
    Song (String name, String artist, InetAddress ip)
    {
        this.artist=artist;
        this.ip=ip;
        this.songname=name;
    }

    public String getSongname() {
        return songname;
    }

    public InetAddress getIp() {
        return ip;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songname='" + songname + '\'' +
                ", artist='" + artist + '\'' +
                ", ip=" + ip +
                '}';
    }
}
