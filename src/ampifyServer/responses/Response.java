package ampifyServer.responses;

public class Response {
    public ResponseCode code;
    public String message;

    public Response(ResponseCode code, String message){
        this.message = message;
        this.code = code;
    }
}
