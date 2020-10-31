package commonPackages.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{
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

    public static boolean search(ArrayList<Group> g1,ArrayList<Group> g2){
        int l1 = g1.size();
        int l2 = g2.size();
        for (int i = 0; i < l1; i++) {
            for (int j = 0; j < l2; j++) {
                if(g1.get(i).id == g2.get(j).id)
                    return true;
            }
        }
        return false;
    }
}
