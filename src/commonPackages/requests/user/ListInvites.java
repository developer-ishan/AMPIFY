package commonPackages.requests.user;

import commonPackages.requests.Request;

import java.io.Serializable;

public class ListInvites extends Request implements Serializable {
    private String userId;
    public ListInvites(String userId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
