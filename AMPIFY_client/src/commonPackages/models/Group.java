package commonPackages.models;

import java.io.Serializable;
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

    public String getName() {
        return name;
    }

    public ArrayList<User> getAdmins() {
        return admins;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
