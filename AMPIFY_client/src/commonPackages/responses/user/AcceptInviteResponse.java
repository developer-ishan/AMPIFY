package commonPackages.responses.user;

import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class AcceptInviteResponse extends Response {
    public AcceptInviteResponse(ResponseCode code, String message){
        super(code,message);
    }
}
