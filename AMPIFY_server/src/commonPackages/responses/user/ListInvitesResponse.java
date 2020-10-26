package commonPackages.responses.user;

import commonPackages.models.Group;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

import java.io.Serializable;
import java.util.ArrayList;

public class ListInvitesResponse extends Response implements Serializable {
    ArrayList<Group> invites;
    public ListInvitesResponse(ResponseCode code, String message,ArrayList<Group> invites){
        super(code,message);
        this.invites = invites;
    }

    @Override
    public String toString() {
        return "ListInvitesResponse{" +
                "invites="+ invites+
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public ArrayList<Group> getInvites() {
        return invites;
    }
}
