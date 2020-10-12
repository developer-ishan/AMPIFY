package commonPackages.requests.group;

import commonPackages.requests.Request;

public class ListInvites extends Request {
    private String userId;
    public ListInvites(String userId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
