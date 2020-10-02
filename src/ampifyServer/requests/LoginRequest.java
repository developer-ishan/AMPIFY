package ampifyServer.requests;


public class LoginRequest extends Request{
    public String email, passwd;
    public LoginRequest(String email, String passwd){
        super(ResquestCode.LOGIN);
        this.email = email;
        this.passwd = passwd;
    }
}
