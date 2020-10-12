package commonPackages.responses.group;

import ampifyServer.requestHandler.GroupHandler;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class Invites extends Response {
    GroupHandler groupHandlers[];
    public Invites(ResponseCode code, String message, GroupHandler[] groupHandlers){
        super(code,message);
        this.groupHandlers = groupHandlers;
    }
}
