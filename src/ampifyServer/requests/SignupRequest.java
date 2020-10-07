package ampifyServer.requests;

public class SignupRequest extends Request{
    public String name, email, passwd;
    public SignupRequest(String name, String email, String passwd){
        super(ResquestCode.SIGNUP);
        this.email = email;
        this.name = name;
        this.passwd = passwd;
    }
}
