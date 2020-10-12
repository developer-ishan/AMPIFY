package ampifyServer.models;

import ampifyServer.requests.group.CreateGroup;
import ampifyServer.requests.group.InviteUser;
import ampifyServer.responses.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
    private String id;
    private String name;
    private String date;
    private ArrayList<User> admins;
    private ArrayList<User> members;
    private ArrayList<User> invites;
    private ArrayList<Playlist> playlists;

    public Group(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public boolean equals(Group g) {
        if(this.id.equals(g.getId())){
            return true;
        }
        return false;
    }
    public boolean equals(String id){
        if(this.id.equals(id))
            return true;
        return false;
    }

    public ArrayList<User> getAdmins(Connection con) throws SQLException{
        this.admins = new ArrayList<User>();
        String query = "SELECT u.*, g.isAdmin\n" +
                "FROM user AS u \n" +
                "JOIN group_membership as g \n" +
                "ON u.u_id = g.u_id \n" +
                "WHERE g.g_id = ? AND g.accepted = true AND g.isAdmin = true;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,this.id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            admins.add(new User(
                    rs.getString("name"),
                    rs.getString("id"),
                    rs.getString("email")

            ));
        }
        return admins;
    }

    public ArrayList<User> getMembers(Connection con) throws SQLException{
        this.members = new ArrayList<User>();
        String query = "SELECT u.*, g.isAdmin\n" +
                "FROM user AS u \n" +
                "JOIN group_membership as g \n" +
                "ON u.u_id = g.u_id \n" +
                "WHERE g.g_id = ? AND g.accepted = true;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,this.id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            members.add(new User(
                    rs.getString("name"),
                    rs.getString("id"),
                    rs.getString("email")

            ));
        }
        return members;
    }
    public ArrayList<User> getInvites(Connection con) throws SQLException{
        this.invites = new ArrayList<User>();
        String query = "SELECT u.*, g.isAdmin\n" +
                "FROM user AS u \n" +
                "JOIN group_membership as g \n" +
                "ON u.u_id = g.u_id \n" +
                "WHERE g.g_id = ? AND g.accepted = false;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,this.id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            admins.add(new User(
                    rs.getString("name"),
                    rs.getString("id"),
                    rs.getString("email")

            ));
        }
        return invites;
    }


    //These are the requests made by the user and must me handled by request handlers
    public Response makeAdmin(Connection con){
        return null;
    }
    public Response removeAdmin(Connection con){
        return null;
    }
    public Response removeMember(Connection con){
        return null;
    }
    public static Response create(CreateGroup req, Connection con) throws SQLException{return null;}
    public static Response invite(InviteUser req, Connection con) throws SQLException{return null;}
    @Override
    public String toString() {
        return "Group{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
