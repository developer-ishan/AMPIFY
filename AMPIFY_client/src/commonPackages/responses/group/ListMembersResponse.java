package commonPackages.responses.group;

import commonPackages.models.GroupMember;
import commonPackages.models.User;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

import java.util.ArrayList;

public class ListMembersResponse extends Response {
    private ArrayList<GroupMember> groupMembers;
    public ListMembersResponse(ResponseCode code, String message, ArrayList<GroupMember> groupMembers){
        super(code,message);
        this.groupMembers = groupMembers;
    }

    public ArrayList<GroupMember> getGroupMembers() {
        return groupMembers;
    }
}
