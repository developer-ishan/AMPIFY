package commonPackages.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Song implements Serializable {
    private ArrayList<Artist> artists;
    private String year;
    private String genre;
    private String name;
    private String id;
    private double duration;
    private long likes;
    private String lyrics;

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public String getGenre() {
        return genre;
    }

    public double getDuration() {
        return duration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public long getLikes() {
        return likes;
    }

    public String getLyrics() {
        return lyrics;
    }

    @Override
    public String toString() {
        return "Song{" +
                "artists=" + artists +
                ", year='" + year + '\'' +
                ", genre='" + genre + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", duration=" + duration +
                ", likes=" + likes +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}
