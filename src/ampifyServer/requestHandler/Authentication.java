package ampifyServer.requestHandler;

import static ampifyServer.requests.ResquestCode.*;

import ampifyServer.requests.LoginRequest;
import ampifyServer.requests.Request;

import static ampifyServer.responses.ResponseCode.*;

import ampifyServer.requests.SignupRequest;
import ampifyServer.responses.LoginResponse;
import ampifyServer.responses.Response;
import ampifyServer.responses.SignupResponse;


import ampifyServer.mainClasses.dbConnection;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {
    public static Response signup(SignupRequest req){
        String u_id = req.email;
        String name = req.name;
        String passwd = req.passwd;
        String email = req.email;

        String query = "INSERT INTO " +
                "user(u_id, name, email, password)" +
                " VALUES(?, ?, ?, ?)";

        Connection con = dbConnection.connect();
        try (PreparedStatement preStat = con.prepareStatement(query)) {
            preStat.setString(1, u_id);
            preStat.setString(2, name);
            preStat.setString(3, email);
            preStat.setString(4, passwd);

            preStat.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new SignupResponse(FAILURE,"Cammot create new user");
        }finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return new SignupResponse(SUCCESS,"Welcome to ampify");
    }
    public static Response login(LoginRequest req){
        System.out.println(req.email);
        System.out.println(req.passwd);
        String email = req.email;
        String passwd = req.passwd;
        Response res;

        String query = String.format("SELECT * FROM user WHERE " +
                "email = '%s' AND " +
                "password = '%s'", email,passwd);

        Connection con = dbConnection.connect();

        PreparedStatement preStat = null;
        ResultSet result = null;

        try {
            preStat = con.prepareStatement(query);
            result = preStat.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(result == null){
            res = new Response(DENIED,"Invalid Credentials.");
            return res;
        }
        res = new LoginResponse(SUCCESS,"Welcome");
        res.message = String.format("Welcome %s",req);

        return res;
    }
}
