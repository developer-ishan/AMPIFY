package commonPackages.responses.user;

import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class LeaveGroupResponse extends Response {
    public LeaveGroupResponse(ResponseCode code, String message){
        super(code,message);
    }
}
