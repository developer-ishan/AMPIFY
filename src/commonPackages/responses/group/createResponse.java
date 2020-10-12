package commonPackages.responses.group;

import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class createResponse extends Response {
    public String groupId;
    public createResponse(ResponseCode code, String message){
        super(code,message);
    }
    public createResponse(ResponseCode code, String message,String groupId){
        super(code,message);
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "groupResponse{" +
                "groupId='" + groupId + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
