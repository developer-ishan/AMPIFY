package commonPackages.responses.auth;

import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class LoginResponse extends Response {
    private String token;
    private String userId;
    public LoginResponse(ResponseCode code, String message){
        super(code,message);
    }
    public LoginResponse(ResponseCode code, String message, String token, String userId) {
        super(code, message);
        this.token = token;
        this.userId = userId;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
