package ampifyServer.responses.group;

import ampifyServer.requestHandler.GroupHandler;
import ampifyServer.responses.Response;
import ampifyServer.responses.ResponseCode;

public class Invites extends Response {
    GroupHandler groupHandlers[];
    public Invites(ResponseCode code, String message, GroupHandler[] groupHandlers){
        super(code,message);
        this.groupHandlers = groupHandlers;
    }
}
