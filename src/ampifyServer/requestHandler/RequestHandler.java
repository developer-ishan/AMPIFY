package AMPIFY_server.src.ampifyServer.requestHandler;

import AMPIFY_server.src.commonPackages.requests.Request;
import AMPIFY_server.src.commonPackages.requests.auth.LoginRequest;
import AMPIFY_server.src.commonPackages.requests.auth.SignupRequest;
import AMPIFY_server.src.commonPackages.requests.user.AcceptInvite;
import AMPIFY_server.src.commonPackages.requests.user.DeclineInvite;
import AMPIFY_server.src.commonPackages.requests.user.ListGroups;
import AMPIFY_server.src.commonPackages.requests.user.ListInvites;
import AMPIFY_server.src.commonPackages.responses.Response;
import AMPIFY_server.src.commonPackages.requests.group.*;
import java.sql.Connection;
import java.sql.SQLException;

public class RequestHandler {
    public static Response getResponse(Request req, Connection con) throws SQLException {
        if(req instanceof SignupRequest){
            return UserRequestsHandler.signup((SignupRequest) req,con);
        }
        //Authentication requests
        else if(req instanceof LoginRequest){
            return UserRequestsHandler.login((LoginRequest) req,con);
        }
        else if(req instanceof ListInvites){
            return UserRequestsHandler.getInvites((ListInvites) req,con);
        }
        //Groups requests
        else if(req instanceof AcceptInvite){
            return UserRequestsHandler.acceptInvite((AcceptInvite) req,con);
        }
        else if(req instanceof DeclineInvite){
            return UserRequestsHandler.declineInvite((DeclineInvite)req,con);
        }
        else if(req instanceof ListGroups){
            return UserRequestsHandler.getGroups((ListGroups) req,con);
        }
        else if(req instanceof ListInvites){
            return UserRequestsHandler.getInvites((ListInvites) req,con);
        }
        else if(req instanceof CreateGroup){
            return GroupRequestsHandler.create((CreateGroup) req,con);
        }
        else if (req instanceof InviteUser){
            return GroupRequestsHandler.invite((InviteUser) req,con);
        }
        else if (req instanceof LeaveGroup){
            return UserRequestsHandler.leaveGroup((LeaveGroup) req,con);
        }
        else if (req instanceof ListMembers){
            return GroupRequestsHandler.getMembers((ListMembers) req,con);
        }
        else if (req instanceof MakeAdmin){
            return GroupRequestsHandler.makeAdmin((MakeAdmin) req,con);
        }
        else{
            return null;
        }
    }
}
