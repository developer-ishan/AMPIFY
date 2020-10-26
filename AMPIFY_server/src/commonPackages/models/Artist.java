package commonPackages.models;

import java.util.ArrayList;

public class Artist extends User{
    private ArrayList<Song> songs;
    private String activeFrom;
    public Artist(String name,String id, String activeFrom,String email){
        super(name,id,email);
        this.activeFrom = activeFrom;

    }
}