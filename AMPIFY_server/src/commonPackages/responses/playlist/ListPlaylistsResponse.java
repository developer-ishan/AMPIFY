package commonPackages.responses.playlist;

import commonPackages.models.Playlist;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

import java.util.ArrayList;

public class ListPlaylistsResponse extends Response {

    private ArrayList<Playlist> playlists;
    public ListPlaylistsResponse(ResponseCode code, String message, ArrayList<Playlist> playlists) {
        super(code, message);
        this.playlists = playlists;
    }
    public ListPlaylistsResponse(ResponseCode code, String message){
        super(code, message);
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
}
