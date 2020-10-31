package commonPackages.requests.playlist;

import commonPackages.requests.Request;

public class CreatePlaylist extends Request {
    private String token;
    private String name;
    private int type;
    private String groupId;

    public CreatePlaylist(String token, String name){
        this.name = name;
        this.token = token;
        this.type = 1;
    }
    public CreatePlaylist(String token, String name,int type,String groupId){
        this.name = name;
        this.token = token;
        this.type = type;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getToken() {
        return token;
    }
}
