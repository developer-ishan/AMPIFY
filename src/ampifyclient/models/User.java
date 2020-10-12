package ampifyclient.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private String name;
    private String id;
    private String email;
    private String password;
    private String doj;
    //group info
    private ArrayList<Song> likes;
    private ArrayList<Group> groups;
    private ArrayList<Group> invites;
    //playlists
    private ArrayList<Playlist> playlists;
    private ArrayList<History> history;

    public User(String name, String id, String email){
        this.name = name;
        this.id = id;
        this.email = email;
    }
    public User(String id){
        this.id = id;
    }

    //Group info
    public ArrayList<Group> getGroups(Connection con) throws SQLException{
        groups = new ArrayList<Group>();
        String query = "SELECT groups.* " +
                "FROM groups " +
                "JOIN group_membership " +
                "ON group_membership.g_id = groups.g_id " +
                "WHERE group_membership.u_id=?  AND group_membership.accepted = true";
        PreparedStatement preStat = con.prepareStatement(query);
        preStat.setString(1,id);
        ResultSet rs = preStat.executeQuery();

        while(rs.next()){
            groups.add(new Group(rs.getString("g_id"),rs.getString("name")));
        }

        return groups;
    }
    public ArrayList<Group> getInvites(Connection con) throws SQLException{
        invites = new ArrayList<Group>();
        String query = "SELECT groups.* " +
                "FROM groups " +
                "JOIN group_membership " +
                "ON group_membership.g_id = groups.g_id " +
                "WHERE group_membership.u_id=?  AND group_membership.accepted = false";
        PreparedStatement preStat = con.prepareStatement(query);
        preStat.setString(1,id);
        ResultSet rs = preStat.executeQuery();

        while(rs.next()){
            invites.add(new Group(rs.getString("g_id"),rs.getString("name")));
        }

        return invites;
    }
    public void acceptInvite(String id, Connection con) throws SQLException{
        boolean flag = false;
        int i;
        for (i = 0; i < groups.size(); i++) {
            if(groups.get(i).equals(id)){
                flag = true;
                break;
            }
        }
        if (flag){
            System.out.println("You are already member of this group.");
            return;
        }
        else{
            flag = false;
            for (i = 0; i < invites.size(); i++) {
                if(invites.get(i).equals(id)){
                    flag = true;
                    break;
                }
            }
            if (flag){
                invites.remove(i);
                String query = "UPDATE group_membership " +
                        "SET accepted = true " +
                        "WHERE u_id=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1,this.id);
                ps.executeUpdate();
                System.out.println("You are a member of this group now.");
            }
            else {
                System.out.println("You are not invited to this group.");
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}