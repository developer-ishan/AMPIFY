package commonPackages.responses.playlist;

import commonPackages.models.Song;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class AddSongResponse extends Response {

    private Song song;
    public AddSongResponse(ResponseCode code, String message) {
        super(code, message);
    }
    public AddSongResponse(ResponseCode code, String message, Song song) {
        super(code, message);
        this.song = song;
    }

    public Song getSong() {
        return song;
    }
}
