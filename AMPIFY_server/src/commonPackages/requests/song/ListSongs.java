package commonPackages.requests.song;

import commonPackages.requests.Request;

public class ListSongs extends Request {
    private String token;

    public ListSongs(String token){
        this.token=token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
