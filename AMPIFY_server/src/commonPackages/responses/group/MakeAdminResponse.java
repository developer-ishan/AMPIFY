package commonPackages.responses.group;

import commonPackages.requests.group.MakeAdmin;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class MakeAdminResponse extends Response {
    public MakeAdminResponse(ResponseCode code, String message){
        super(code,message);
    }
}
