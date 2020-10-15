package commonPackages.models;

import commonPackages.requests.group.CreateGroup;
import commonPackages.requests.group.InviteUser;
import commonPackages.responses.Response;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group implements Serializable {
    private String id;
    private String name;
    private String date;
    private ArrayList<User> admins;
    private ArrayList<User> members;
    private ArrayList<User> invites;
    private ArrayList<Playlist> playlists;

    public Group(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public boolean equals(Group g) {
        if(this.id.equals(g.getId())){
            return true;
        }
        return false;
    }
    public boolean equals(String id){
        if(this.id.equals(id))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
