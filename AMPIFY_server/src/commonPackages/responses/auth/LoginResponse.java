package commonPackages.responses.auth;

import commonPackages.models.User;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class LoginResponse extends Response {
    private String token;
    private User user;
    public LoginResponse(ResponseCode code, String message){
        super(code,message);
    }
    public LoginResponse(ResponseCode code, String message, String token, User user) {
        super(code, message);
        this.token = token;
        this.user = user;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
