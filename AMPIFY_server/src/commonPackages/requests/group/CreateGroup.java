package commonPackages.requests.group;

import commonPackages.requests.Request;

public class CreateGroup extends Request {
    private String token;
    private String name;
    public CreateGroup(String token, String name){
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getToken() {
        return token;
    }
}
