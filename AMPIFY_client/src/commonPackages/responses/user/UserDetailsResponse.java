package commonPackages.responses.user;

import commonPackages.models.User;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class UserDetailsResponse extends Response {
    private User user;
    public UserDetailsResponse(ResponseCode code,String message, User user){
        super(code, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
