package commonPackages.responses.user;

import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class DeclineInviteResponse extends Response {
    public DeclineInviteResponse(ResponseCode code, String message){
        super(code,message);
    }
}
