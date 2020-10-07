package ampifyServer.mainClasses;

import ampifyServer.requestHandler.Authentication;
import ampifyServer.requests.LoginRequest;
import ampifyServer.responses.Response;

public class amplifyServer {
    public static void main(String[] args) {

//        Request req  = new SignupRequest("warmachine","ishan@mnnit.ac.in","qwerty");
//        Response res =  Authentication.signup((SignupRequest) req);
//        System.out.println(res.code+"\n"+res.message);

        LoginRequest req = new LoginRequest("ishan@mnnit.ac.in","qwerty");
        Response res = Authentication.login(req);
        System.out.println(res.code+"\n"+res.message);
    }
}
