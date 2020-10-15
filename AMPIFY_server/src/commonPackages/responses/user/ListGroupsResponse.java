package commonPackages.responses.user;

import commonPackages.models.Group;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

import java.util.ArrayList;

public class ListGroupsResponse extends Response {
    private ArrayList<Group> groups;
    public ListGroupsResponse(ResponseCode code, String message, ArrayList<Group> groups){
        super(code, message);
        this.groups = groups;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }
}
