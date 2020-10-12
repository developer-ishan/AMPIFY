package ampifyclient.requests.auth;

import ampifyclient.requests.Request;

public class SignupRequest extends Request {
    private String name, email, passwd;
    public SignupRequest(String name, String email, String passwd){
        this.email = email;
        this.name = name;
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }
}
