package ampifyServer.requestHandler;

import commonPackages.models.Group;
import commonPackages.models.GroupMember;
import commonPackages.requests.group.CreateGroup;
import commonPackages.requests.group.InviteUser;

import commonPackages.requests.group.ListMembers;
import commonPackages.requests.group.MakeAdmin;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.group.InviteResponse;
import commonPackages.responses.group.ListMembersResponse;
import commonPackages.responses.group.MakeAdminResponse;
import commonPackages.responses.group.CreateGroupResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GroupRequestsHandler{
    public static int getRole(String userId,String groupId ,Connection con) throws SQLException{
        //0: not member
        //1: invied not accepted
        //2: member not admin
        //3: member and an admin
        PreparedStatement ps;
        String query;
        ResultSet rs;
        //Check if the given user is an admin or not
        query = "Select * from group_membership where g_id=? and u_id=?";
        ps = con.prepareStatement(query);
        ps.setString(1,groupId);
        ps.setString(2,userId);
        rs = ps.executeQuery();

        if(rs.next()){
            if(rs.getBoolean("isAdmin"))
                return 3;
            else if(rs.getInt("status")==1)
                return 2;
            else
                return 1;
        }else{
            return 0;
        }
    }
    public static Response create(CreateGroup req, Connection con) throws SQLException{
        String groupId = UUID.randomUUID().toString();
        String name = req.getName();
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new CreateGroupResponse(ResponseCode.DENIED,"Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new CreateGroupResponse(ResponseCode.DENIED,"Login first.");
        }

        String query1 = "INSERT INTO groups(g_id,name) VALUES(?, ?)";
        String query2 = "INSERT INTO group_membership(u_id, g_id, status, isAdmin) values(?, ?, ?, ?)";

        PreparedStatement preStat1 = con.prepareStatement(query1);
        PreparedStatement preStat2 = con.prepareStatement(query2);

        preStat1.setString(1,groupId);
        preStat1.setString(2,name);

        preStat2.setString(1,userId);
        preStat2.setString(2,groupId);
        preStat2.setInt(3,1);
        preStat2.setBoolean(4,true);

        try {
            preStat1.executeUpdate();
            preStat2.executeUpdate();
            Response res = new CreateGroupResponse(ResponseCode.SUCCESS,"Group Created Successfully",new Group(groupId,name));
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Group Creation failed.");
        }
        return new CreateGroupResponse(ResponseCode.FAILURE,"Group Creating Failed");
    }
    public static Response invite(InviteUser req, Connection con) throws SQLException{
        String by;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                by = token.getSubject();
            } else {
                return new InviteResponse(ResponseCode.DENIED,"Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new InviteResponse(ResponseCode.DENIED,"Login first.");
        }
        String userId = req.getUserId();
        String groupId = req.getGroupId();
        PreparedStatement ps;
        String query;
        ResultSet rs;
        //Check if the given user is an admin or not
        int role = getRole(by,groupId,con);

        if(role <= 1){
            return new InviteResponse(ResponseCode.FAILURE,"You are not a member of this group.");
        }else if (role == 2){
            return new InviteResponse(ResponseCode.DENIED,"You are not an admin of this group.");
        } else {
            query = "insert into group_membership(u_id, g_id) values(?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1,userId);
            ps.setString(2,groupId);
            try{
                ps.executeUpdate();
            } catch (SQLException e){
                return new InviteResponse(ResponseCode.FAILURE,"User is already invited.");
            }
            return new InviteResponse(ResponseCode.SUCCESS,"Invite is sent");
        }

    }
    public static Response makeAdmin(MakeAdmin req, Connection con) throws SQLException{
        String by;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                by = token.getSubject();
            } else {
                return new MakeAdminResponse(ResponseCode.DENIED,"Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new MakeAdminResponse(ResponseCode.DENIED,"Login first.");
        }
        String userId = req.getUserId();
        String groupId = req.getGroupId();
        int role = getRole(by,groupId,con);

        if(role <= 1){
            return new MakeAdminResponse(ResponseCode.FAILURE,"You are not a member of this group.");
        } else if(role == 2){
            return new MakeAdminResponse(ResponseCode.DENIED,"You are not an admin of this group.");
        } else {
            String query = "UPDATE group_membership" +
                    "SET isAdmin = true" +
                    "WHERE u_id = ? AND g_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,userId);
            ps.setString(2,groupId);
            ps.executeUpdate();
            return new MakeAdminResponse(ResponseCode.SUCCESS,"User is Successfully made admin.");
        }
    }
    public static Response getMembers(ListMembers req, Connection con) throws SQLException{
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new ListMembersResponse(ResponseCode.DENIED,"Login first.",null);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ListMembersResponse(ResponseCode.DENIED,"Login first.",null);
        }
        String groupId = req.getGroupId();

        int role = getRole(userId,groupId,con);
        if(role<=1){
            return new ListMembersResponse(ResponseCode.DENIED,"You are not member of this group",null);
        } else {
            ArrayList<GroupMember> groupMembers = new ArrayList<GroupMember>();
            String query = "SELECT u.*, g.isAdmin\n, g.status" +
                    "FROM user AS u \n" +
                    "JOIN group_membership as g \n" +
                    "ON u.u_id = g.u_id \n" +
                    "WHERE g.g_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,groupId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                groupMembers.add(new GroupMember(
                        rs.getString("name"),
                        rs.getString("id"),
                        rs.getString("email"),
                        role
                ));
            }
            return new ListMembersResponse(ResponseCode.SUCCESS,"Here are the members of this group.",groupMembers);
        }
    }
}