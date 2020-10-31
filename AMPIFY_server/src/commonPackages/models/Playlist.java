package commonPackages.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {
    private String id;
    private String name;
    /*
    Types
    * 1: not public
    * 2: public
    Roles
    * 1: my own creation
    * 2: shared with me
    * */
    private int type;
    private int role;
    private String date;
    private ArrayList<Song> songs;

    public Playlist(String id, String name){
        this.id = id;
        this.name = name;
        this.type = 1;
        this.role = 1;
    }
    public Playlist(String id, String name, int type){
        this.id = id;
        this.name = name;
        this.type = type;
        this.role = 1;
    }
    public  Playlist(){

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public int getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
