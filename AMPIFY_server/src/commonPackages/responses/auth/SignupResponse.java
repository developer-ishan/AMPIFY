package commonPackages.responses.auth;

import commonPackages.models.User;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class SignupResponse extends Response {
    User user;
    public SignupResponse(ResponseCode code, String message){
        super(code,message);
    }
    public SignupResponse(ResponseCode code, String message, User user){
        super(code,message);
        this.user = user;
    }


    @Override
    public String toString() {
        return "SignupResponse{" +
                "user='" + user + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
