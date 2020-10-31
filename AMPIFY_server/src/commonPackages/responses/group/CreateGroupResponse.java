package commonPackages.responses.group;

import commonPackages.models.Group;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class CreateGroupResponse extends Response {
    public Group group;
    public CreateGroupResponse(ResponseCode code, String message){
        super(code,message);
    }
    public CreateGroupResponse(ResponseCode code, String message, Group group){
        super(code,message);
        this.group = group;
    }

    @Override
    public String toString() {
        return "groupResponse{" +
                "groupId='" + group.getName() + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public Group getGroup() {
        return group;
    }
}
