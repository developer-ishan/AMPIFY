package ampifyServer.requestHandler;

import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class RequestHandler {
    public static Response getResponse(Request req, Connection con) throws SQLException {
        if(req instanceof SignupRequest){
            return UserRequestsHandler.signup((SignupRequest) req,con);
        }
        else if(req instanceof LoginRequest){
            return UserRequestsHandler.login((LoginRequest) req,con);
        }
        else {
            return null;
        }
    }
}
