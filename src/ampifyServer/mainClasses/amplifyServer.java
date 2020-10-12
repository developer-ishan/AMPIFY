package ampifyServer.mainClasses;

import ampifyServer.SocketServer.SocketServer;
import ampifyServer.models.Group;
import ampifyServer.models.User;
import ampifyServer.requestHandler.Authentication;
import ampifyServer.requests.Request;
import ampifyServer.requests.auth.LoginRequest;
import ampifyServer.requests.auth.SignupRequest;
import ampifyServer.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class amplifyServer implements Runnable {
    static Connection con;
    public static void main(String[] args) {

        try {
            con = dbConnection.connect();
            Thread thread=new Thread(new amplifyServer());
            thread.start();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Cannot Connect to DB");
            }
    }

    @Override
    public void run() {
            System.out.println(".....Waiting.....for.....Client.....");
            Request request= SocketServer.getRequestObject();
            if(request instanceof LoginRequest)
            {
                System.out.println("executing login....");
                Response response = null;
                try {
                    response = Authentication.login((LoginRequest)request,con);
                } catch (SQLException throwables) {
                    System.err.println("Error in Login :::: "+throwables);
                }
                SocketServer.SendResponseObject(response);
            }
            else if(request instanceof SignupRequest)
            {
                Response response=null;
                try {
                    response = Authentication.signup((SignupRequest) request,con);
                } catch (SQLException throwables) {
                    System.err.println("Error in Signup :::: "+throwables);
                }
                SocketServer.SendResponseObject(response);
            }
            /*User u = new User("a");
            ArrayList<Group> groups;

            System.out.println("Invites are :- ");
            groups = u.getGroups(con);
            for(Group g: groups){
                System.out.println(g);
            }

            System.out.println("Groups are :- ");
            groups = u.getGroups(con);
            for(Group g: groups){
                System.out.println(g);
            }

            System.out.println("Accepting the invite:-");
            u.acceptInvite("g2", con);

            ArrayList<Group> groups2 = u.getInvites(con);
            for(Group g: groups2){
                System.out.println(g);
            }
            System.out.println("Groups are :- ");
            groups = u.getGroups(con);
            for(Group g: groups){
                System.out.println(g);
            }*/
    }
}
