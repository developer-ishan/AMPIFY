package commonPackages.models;

import java.io.Serializable;

public class GroupMember extends User implements Serializable {
    private int role;
    public GroupMember(String name, String id, String email, int role){
        super(name,id,email);
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
                "role=" + role +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
