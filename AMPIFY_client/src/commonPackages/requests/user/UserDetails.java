package commonPackages.requests.user;

import commonPackages.requests.Request;

public class UserDetails extends Request {
    private String token;
    public UserDetails(String token){
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
