package commonPackages.requests.group;

import commonPackages.models.User;
import commonPackages.requests.Request;

public class MakeAdmin extends Request {
    private String userId;
    private String groupId;
    private String by;
    public MakeAdmin(String userId, String groupId, String by){
        this.by = by;
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getBy() {
        return by;
    }
}
