package commonPackages.requests.user;

import commonPackages.requests.Request;

public class ListGroups extends Request {
    private String userId;
    public ListGroups(String userId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
