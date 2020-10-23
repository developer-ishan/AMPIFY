package commonPackages.responses.auth;

import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;

public class InvalidToken extends Response {

    public InvalidToken() {
        super(ResponseCode.DENIED,"Login first.");
    }
}
