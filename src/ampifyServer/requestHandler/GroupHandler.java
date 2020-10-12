package ampifyServer.requestHandler;

import ampifyServer.requests.group.CreateGroup;
import ampifyServer.requests.group.InviteUser;

import ampifyServer.responses.Response;
import ampifyServer.responses.ResponseCode;
import ampifyServer.responses.group.createResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GroupHandler{
    public static Response create(CreateGroup req, Connection con) throws SQLException{
        String groupId = UUID.randomUUID().toString();;
        String name = req.getName();
        String userId = req.getUserId();

        String query1 = "INSERT INTO groups(g_id,name) VALUES(?, ?)";
        String query2 = "INSERT INTO group_membership(u_id, g_id, accepted, isAdmin) values(?, ?, ?, ?)";

        PreparedStatement preStat1 = con.prepareStatement(query1);
        PreparedStatement preStat2 = con.prepareStatement(query2);

        preStat1.setString(1,groupId);
        preStat1.setString(2,name);

        preStat2.setString(1,userId);
        preStat2.setString(2,groupId);
        preStat2.setBoolean(3,true);
        preStat2.setBoolean(4,true);

        try {
            preStat1.executeUpdate();
            preStat2.executeQuery();
            Response res = new createResponse(ResponseCode.SUCCESS,"Group Created Successfully",groupId);
            return res;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Group Creation failed.");
        }
        return new createResponse(ResponseCode.FAILURE,"Group Creating Failed");
    }

    public static Response invite(InviteUser req, Connection con) throws SQLException{
        String by = req.getBy();
        String userId = req.getUserId();
        String groupId = req.getGroupId();
        boolean isAdmin = false;

        PreparedStatement ps;
        String query;
        ResultSet rs;
        //Check if the given user is an admin or not
        query = "Select * from group_membership where g_id=? and u_id=?";
        ps = con.prepareStatement(query);
        ps.setString(1,groupId);
        ps.setString(2,by);
        rs = ps.executeQuery();

        if(rs.next()){
            isAdmin = rs.getBoolean("isAdmin");
        }else{
            return new Response(ResponseCode.FAILURE,"You are not a member of this group.");
        }

        if(isAdmin){
            query = "insert into group_membership(u_id, g_id) values(?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1,userId);
            ps.setString(2,groupId);
            try{
                ps.executeUpdate();
            } catch (SQLException e){
                return new Response(ResponseCode.FAILURE,"User is already invited.");
            }


            return new Response(ResponseCode.SUCCESS,"Invite is sent");
        }else {
            return new Response(ResponseCode.DENIED,"You are not an admin of this group.");
        }
    }
}