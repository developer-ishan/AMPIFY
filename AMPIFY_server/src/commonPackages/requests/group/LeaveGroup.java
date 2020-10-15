package commonPackages.requests.group;

import commonPackages.requests.Request;

public class LeaveGroup extends Request {
    private String userId;
    private String groupId;

    public LeaveGroup(String userId, String groupId){
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
