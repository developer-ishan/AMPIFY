package commonPackages.requests.playlist;

import commonPackages.requests.Request;

public class AddSong extends Request {
    private String token;
    private String songId;
    private String playlistId;
    public AddSong(String token, String songId, String playlistId){
        this.token = token;
        this.songId = songId;
        this.playlistId = playlistId;
    }

    public String getSongId() {
        return songId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    @Override
    public String getToken() {
        return token;
    }
}
