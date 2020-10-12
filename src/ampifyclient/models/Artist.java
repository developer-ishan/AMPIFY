package ampifyclient.models;

import java.util.ArrayList;

public class Artist extends User {
    private ArrayList<Song> songs;
    public Artist(String name,String id,String email){
        super(name,id,email);
    }
}