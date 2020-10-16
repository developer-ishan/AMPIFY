package commonPackages.requests.group;

import commonPackages.requests.Request;

public class CreateGroup extends Request {
    private String name;
    private String userId;
    public CreateGroup(String userId, String name){
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public String getUserId() {
        return userId;
    }
}
