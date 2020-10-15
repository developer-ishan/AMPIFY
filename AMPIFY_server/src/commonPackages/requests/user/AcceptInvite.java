package commonPackages.requests.user;

import commonPackages.requests.Request;

public class AcceptInvite extends Request {
    private String userId;
    private String groupId;
    public AcceptInvite(String userId, String groupId){
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }
}
