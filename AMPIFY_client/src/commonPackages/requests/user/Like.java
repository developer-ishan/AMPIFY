package commonPackages.requests.user;

import commonPackages.requests.Request;

public class Like extends Request {
    private String token;
    private String songId;

    public Like(String token, String songId){
        this.token = token;
        this.songId = songId;
    }

    @Override
    public String getToken() {
        return token;
    }

    public String getSongId() {
        return songId;
    }
}
