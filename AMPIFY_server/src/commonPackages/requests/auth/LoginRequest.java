package commonPackages.requests.auth;


import commonPackages.requests.Request;

public class LoginRequest extends Request {
    private String email, passwd;
    public LoginRequest(String email, String passwd){
        this.email = email;
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }
}
