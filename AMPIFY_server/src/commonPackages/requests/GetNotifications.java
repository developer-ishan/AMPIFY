package commonPackages.requests;

public class GetNotifications extends Request{
    private String userId;
    public GetNotifications(String userId){
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
