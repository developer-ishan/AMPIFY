package ampifyServer.requestHandler;

import ampifyServer.models.User;
import ampifyServer.requests.auth.LoginRequest;

import static ampifyServer.responses.ResponseCode.*;

import ampifyServer.requests.auth.SignupRequest;
import ampifyServer.responses.auth.LoginResponse;
import ampifyServer.responses.Response;
import ampifyServer.responses.auth.SignupResponse;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Authentication {
    public static Response signup(SignupRequest req, Connection con) throws SQLException{
        String userId = UUID.randomUUID().toString();
        String name = req.getName();
        String passwd = req.getPasswd();
        String email = req.getEmail();

        String query = "INSERT INTO " +
                "user(u_id,name, email, password)" +
                " VALUES(? ,?, ?, ?)";

        PreparedStatement preStat;

        preStat = con.prepareStatement(query);
        preStat.setString(1,userId);
        preStat.setString(2, name);
        preStat.setString(3, email);
        preStat.setString(4, passwd);


        try {
            preStat.executeUpdate();
            return new SignupResponse(SUCCESS,"Welcome to ampify",new User(name,userId,email));
        } catch (SQLException throwables) {
            return new SignupResponse(FAILURE,"User already exists");
        }
    }
    public static Response login (LoginRequest req, Connection con) throws SQLException {
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
            return new LoginResponse(SUCCESS,"Welcome",result.getString("u_id"));
        }
        return new LoginResponse(DENIED,"Invalid Credentials");
    }
}
