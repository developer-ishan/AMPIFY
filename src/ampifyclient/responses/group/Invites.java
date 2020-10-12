package ampifyclient.responses.group;

import ampifyServer.requestHandler.GroupHandler;
import ampifyclient.responses.Response;
import ampifyclient.responses.ResponseCode;

public class Invites extends Response {
    GroupHandler groupHandlers[];
    public Invites(ResponseCode code, String message, GroupHandler[] groupHandlers){
        super(code,message);
        this.groupHandlers = groupHandlers;
    }
}
