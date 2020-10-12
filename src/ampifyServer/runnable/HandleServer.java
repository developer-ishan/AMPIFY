package ampifyServer.runnable;

import ampifyServer.SocketServer.SocketServer;
import ampifyServer.requestHandler.Authentication;
import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;

import java.sql.Connection;
import java.sql.SQLException;

public class HandleServer implements Runnable{
    Connection con;
    public HandleServer(Connection con)
    {
        this.con=con;
    }

    @Override
    public void run() {
        System.out.println(".....Waiting.....for.....Client.....");
        Request request= SocketServer.getRequestObject();
        if(request instanceof LoginRequest)
        {

            System.out.println("executing login....\n"+
                    "user email :::: "+((LoginRequest) request).getEmail()+"\n"
                    +"Current Thread Deatils :::: "+
                    Thread.currentThread().getName()+"  :::: "+Thread.currentThread().getId());

            Response response = null;
            try {
                response = Authentication.login((LoginRequest)request,con);
                System.out.println(response);
            } catch (SQLException throwables) {
                System.err.println("Error in Login :::: "+throwables);
            }

            SocketServer.SendResponseObject(response);
        }
        else if(request instanceof SignupRequest)
        {
           System.out.println("executing Signup....\n"+
                    "user email :::: "+((SignupRequest) request).getEmail()+"\n"
                    +"Current Thread Deatils :::: "+
                    Thread.currentThread().getName()+"  :::: "+Thread.currentThread().getId());

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
