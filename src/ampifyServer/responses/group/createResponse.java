package ampifyServer.responses.group;

import ampifyServer.responses.Response;
import ampifyServer.responses.ResponseCode;

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
