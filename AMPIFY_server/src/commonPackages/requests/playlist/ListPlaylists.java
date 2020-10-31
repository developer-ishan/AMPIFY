package commonPackages.requests.playlist;

import commonPackages.requests.Request;

public class ListPlaylists extends Request {
    private String token;
    public ListPlaylists(String token){
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
