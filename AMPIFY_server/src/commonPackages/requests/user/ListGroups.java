package commonPackages.requests.user;

import commonPackages.requests.Request;

public class ListGroups extends Request {
    private String token;
    public ListGroups(String token){
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
