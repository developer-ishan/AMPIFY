package ampifyServer.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Playlist{
    private String id;
    private String name;
    private String ownerId;
    private String date;
    /*
    Types
    * 1: personal
    * 2: public
    * 3: group
    * */
    private int type;
    private ArrayList<Song> songs;

    public void add(Song s){
        songs.add(s);
    }
    public void remove(Song s){
        songs.remove(s);
    }
    public void delete(Connection con) throws SQLException {

    }
    public void create(Connection con) throws SQLException{

    }
}
