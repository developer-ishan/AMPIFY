package commonPackages.models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    protected String name;
    protected String id;
    protected String email;
    protected String doj;
    protected byte[] image;
    //group info
    protected ArrayList<Song> likes;
    protected ArrayList<Group> groups;
    protected ArrayList<Group> invites;
    //playlists
    protected ArrayList<Playlist> playlists;
    protected ArrayList<History> history;

    public User(String name, String id, String email){
        this.name = name;
        this.id = id;
        this.email = email;
    }
    public User(String id){
        this.id = id;
    }
    public User (){}
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }
}