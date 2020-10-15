package commonPackages.requests.group;

import commonPackages.requests.Request;

public class ListMembers extends Request {
    private String userId;
    private String groupId;

    public ListMembers(String userId, String groupId){
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getUserId() {
        return userId;
    }
}
