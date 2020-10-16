package commonPackages.requests.group;

import commonPackages.requests.Request;

public class InviteUser extends Request {
    private String by, groupId, userId;
    public InviteUser(String userId, String by, String groupId){
        this.by = by;
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getBy() {
        return by;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getUserId() {
        return userId;
    }
}