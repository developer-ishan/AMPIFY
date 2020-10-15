package commonPackages.models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    protected String name;
    protected String id;
    protected String email;
    protected String doj;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}