package ampifyServer.mainClasses;

import ampifyServer.requestHandler.Authentication;
import ampifyServer.requests.LoginRequest;
import ampifyServer.requests.Request;
import ampifyServer.requests.SignupRequest;
import ampifyServer.responses.Response;

import java.sql.Connection;

public class amplifyServer {
    public static void main(String[] args) {

//        Request req  = new SignupRequest("warmachine","ishan@mnnit.ac.in","qwerty");
//        Response res =  Authentication.signup((SignupRequest) req);
//        System.out.println(res.code+"\n"+res.message);

        Request req = new LoginRequest("ishan@mnnit.ac.in","qwerty");
        Response res = Authentication.login((LoginRequest) req);
        System.out.println(res.code+"\n"+res.message);
    }
}
