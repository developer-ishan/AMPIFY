package commonPackages.requests.user;

import commonPackages.requests.Request;

public class AcceptInvite extends Request {
    private String token;
    private String groupId;
    public AcceptInvite(String token, String groupId){
        this.groupId = groupId;
        this.token = token;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getToken() {
        return token;
    }
}
