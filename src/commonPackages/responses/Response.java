package commonPackages.responses;

import java.io.Serializable;

public class Response implements Serializable {
    protected ResponseCode code;
    protected String message;

    public Response(ResponseCode code, String message){
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public ResponseCode getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
