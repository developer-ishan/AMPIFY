package ampifyServer.requests;

import static ampifyServer.requests.ResquestCode.* ;
public abstract class Request {
    public ResquestCode code;
    public Request(ResquestCode code){
        this.code = code;
    }
}
