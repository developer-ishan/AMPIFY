package commonPackages.responses.playlist;

import commonPackages.models.Playlist;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class CreatePlaylistResponse extends Response {
    private Playlist playlist;
    public CreatePlaylistResponse(ResponseCode code, String message,Playlist playlist){
        super(code, message);
        this.playlist = playlist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }
}
