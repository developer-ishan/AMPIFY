package commonPackages.responses.group;

import ampifyServer.requestHandler.GroupRequestsHandler;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class Invites extends Response {
    GroupRequestsHandler groupRequestsHandlers[];
    public Invites(ResponseCode code, String message, GroupRequestsHandler[] groupRequestsHandlers){
        super(code,message);
        this.groupRequestsHandlers = groupRequestsHandlers;
    }
}
