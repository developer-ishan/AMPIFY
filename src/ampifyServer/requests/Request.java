package ampifyServer.requests;

public abstract class Request {
    public ResquestCode code;
    public Request(ResquestCode code){
        this.code = code;
    }
}
