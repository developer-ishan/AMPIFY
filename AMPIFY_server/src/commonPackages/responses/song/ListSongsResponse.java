package commonPackages.responses.song;

import commonPackages.models.Song;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

import java.util.ArrayList;

public class ListSongsResponse extends Response {
    private ArrayList<Song> songs;
    public ListSongsResponse(ResponseCode code, String message, ArrayList<Song> songs){
        super(code, message);
        this.songs = songs;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }
}
