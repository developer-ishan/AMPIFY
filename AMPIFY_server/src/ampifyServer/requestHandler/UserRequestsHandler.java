package ampifyServer.requestHandler;

import commonPackages.models.Group;
import commonPackages.models.User;
import commonPackages.requests.auth.LoginRequest;

import static commonPackages.responses.ResponseCode.*;

import commonPackages.requests.auth.SignupRequest;
import commonPackages.requests.group.LeaveGroup;
import commonPackages.requests.user.AcceptInvite;
import commonPackages.requests.user.DeclineInvite;
import commonPackages.requests.user.ListGroups;
import commonPackages.requests.user.ListInvites;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.auth.LoginResponse;
import commonPackages.responses.auth.SignupResponse;
import commonPackages.responses.group.CreateGroupResponse;
import commonPackages.responses.user.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class UserRequestsHandler{

    public static SignupResponse signup(SignupRequest req, Connection con) throws SQLException{
        String id = UUID.randomUUID().toString();
        String name = req.getName();
        String passwd = req.getPasswd();
        String email = req.getEmail();

        String query = "INSERT INTO " +
                "user(u_id,name, email, password)" +
                " VALUES(? ,?, ?, ?)";

        PreparedStatement preStat;

        preStat = con.prepareStatement(query);
        preStat.setString(1,id);
        preStat.setString(2, name);
        preStat.setString(3, email);
        preStat.setString(4, passwd);


        try {
            preStat.executeUpdate();
            return new SignupResponse(SUCCESS,"Welcome to ampify",new User(name,id,email));
        } catch (SQLException throwables) {
            return new SignupResponse(FAILURE,"User already exists");
        }
    }
    public static LoginResponse login (LoginRequest req, Connection con) throws SQLException {
        String email = req.getEmail();
        String passwd = req.getPasswd();

        String query = "SELECT * FROM user WHERE email = ? AND password = ?";

        PreparedStatement preStat;
        ResultSet result;

        preStat = con.prepareStatement(query);
        preStat.setString(1,email);
        preStat.setString(2,passwd);
        result = preStat.executeQuery();

        if(result.next()){
            String userId = result.getString("u_id");
            setAval(con,userId,true);


            int EXPIRY_DAYS = 30;

            JSONObject jwtPayload = new JSONObject();
            jwtPayload.put("status", 0);

            JSONArray audArray = new JSONArray();
            audArray.put("user");
            jwtPayload.put("sub", userId);

            jwtPayload.put("aud", audArray);
            LocalDateTime ldt = LocalDateTime.now().plusDays(EXPIRY_DAYS);
            jwtPayload.put("exp", ldt.toEpochSecond(ZoneOffset.UTC)); //this needs to be configured
            String token = new JWebToken(jwtPayload).toString();


            return new LoginResponse(SUCCESS,"Welcome",token);
        }
        return new LoginResponse(DENIED,"Invalid Credentials");
    }

    //Group info
    public static ListGroupsResponse getGroups(ListGroups req, Connection con) throws SQLException{
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new ListGroupsResponse(ResponseCode.DENIED,"Login first.",null);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ListGroupsResponse(ResponseCode.DENIED,"Login first.",null);
        }
        ArrayList<Group> groups = new ArrayList<Group>();
        String query = "SELECT groups.* " +
                "FROM groups " +
                "JOIN group_membership " +
                "ON group_membership.g_id = groups.g_id " +
                "WHERE group_membership.u_id=?  AND group_membership.status = 1";
        PreparedStatement preStat = con.prepareStatement(query);
        preStat.setString(1,userId);
        ResultSet rs = preStat.executeQuery();

        while(rs.next()){
            groups.add(new Group(rs.getString("g_id"),rs.getString("name")));
        }

        return new ListGroupsResponse(SUCCESS,"Here are your groups.",groups);
    }
    public static ListInvitesResponse getInvites(ListInvites req, Connection con) throws SQLException{
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new ListInvitesResponse(ResponseCode.DENIED,"Login first.",null);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new ListInvitesResponse(ResponseCode.DENIED,"Login first.",null);
        }
        ArrayList<Group> invites = new ArrayList<Group>();
        String query = "SELECT groups.* " +
                "FROM groups " +
                "JOIN group_membership " +
                "ON group_membership.g_id = groups.g_id " +
                "WHERE group_membership.u_id=?  AND group_membership.status = -1";
        PreparedStatement preStat = con.prepareStatement(query);
        preStat.setString(1,userId);
        ResultSet rs = preStat.executeQuery();

        while(rs.next()){
            invites.add(new Group(rs.getString("g_id"),rs.getString("name")));
        }
        return new ListInvitesResponse(SUCCESS,"Here are your invites.",invites);
    }
    public static AcceptInviteResponse acceptInvite(AcceptInvite req, Connection con) throws SQLException{
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new AcceptInviteResponse(ResponseCode.DENIED,"Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new AcceptInviteResponse(ResponseCode.DENIED,"Login first.");
        }
        String groupId = req.getGroupId();
        int role = GroupRequestsHandler.getRole(userId,groupId,con);

        if(role==1){
            return new AcceptInviteResponse(DENIED,"You are not invited");
        } else if(role ==2){
            String query = "UPDATE group_membership" +
                    "SET status = 1" +
                    "WHERE u_id = ? AND g_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,userId);
            ps.setString(2,groupId);
            ps.executeUpdate();
            return new AcceptInviteResponse(SUCCESS,"You are now member of this group.");
        }
        else {
            return new AcceptInviteResponse(FAILURE,"You are already member of this group.");
        }
    }
    public static DeclineInviteResponse declineInvite(DeclineInvite req, Connection con) throws SQLException{
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new DeclineInviteResponse(ResponseCode.DENIED,"Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new DeclineInviteResponse(ResponseCode.DENIED,"Login first.");
        }
        String groupId = req.getGroupId();
        int role = GroupRequestsHandler.getRole(userId,groupId,con);

        if(role==1){
            return new DeclineInviteResponse(DENIED,"You are not invited");
        } else if(role == 2) {
            String query = "DELETE FROM group_membership WHERE u_id = ? AND g_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, groupId);
            try {
                ps.executeUpdate();
                return new DeclineInviteResponse(SUCCESS, "You have declined the invite.");
            } catch (SQLException e) {
                return new DeclineInviteResponse(FAILURE, "Cannot decline the Invite.");
            }
        }
        return new DeclineInviteResponse(FAILURE, "You are already a member, please leave if you want to.");
    }

    public static LeaveGroupResponse leaveGroup(LeaveGroup req, Connection con) throws SQLException{
        String userId;
        JWebToken token;
        try {
            token = new JWebToken(req.getToken());
            if(token.isValid()){
                userId = token.getSubject();
            } else {
                return new LeaveGroupResponse(ResponseCode.DENIED,"Login first.");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new LeaveGroupResponse(ResponseCode.DENIED,"Login first.");
        }
        String groupId = req.getGroupId();

        String query = "DELETE FROM group_membership WHERE u_id = ? AND g_id = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,userId);
        ps.setString(2,groupId);

        try {
            ps.executeUpdate();
            return new LeaveGroupResponse(SUCCESS,"You have left the group.");
        } catch (SQLException e){
            return new LeaveGroupResponse(FAILURE,"Cannot Leave Group.");
        }
    }

    public static boolean isAval(Connection con, String u_id) throws SQLException{
        String query = "SELECT isAval from user where u_id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1,u_id);
        ResultSet rs = ps.executeQuery();

        return rs.getBoolean(1);
    }
    public static HashMap<String ,Boolean> getAvalUsers(Connection con) throws SQLException{
        String query = "SELECT u_id, isAval from user";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        HashMap<String ,Boolean> avalUsers = new HashMap<>();

        while (rs.next()){
            if(!rs.getBoolean(2))
                continue;
            avalUsers.put(rs.getString(1),rs.getBoolean(2));
        }
        return avalUsers;
    }
    public static void setAval(Connection con, String u_id, boolean status) throws SQLException{
        String query = "UPDATE user SET isAval=? WHERE u_id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setBoolean(1,status);
        ps.setString(2,u_id);
        ps.executeUpdate();
    }
}