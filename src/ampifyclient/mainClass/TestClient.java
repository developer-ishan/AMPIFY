package ampifyclient.mainClass;

import commonPackages.models.Group;
import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.requests.group.CreateGroup;
import commonPackages.requests.user.ListGroups;
import commonPackages.requests.user.ListInvites;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.auth.LoginResponse;
import commonPackages.responses.auth.SignupResponse;
import commonPackages.responses.group.CreateGroupResponse;
import commonPackages.responses.user.ListGroupsResponse;
import commonPackages.responses.user.ListInvitesResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TestClient {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ooi;
    public TestClient(Socket socket) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ooi = new ObjectInputStream(socket.getInputStream());
    }
    public static void main(String[] args) {
        String userId = null;
        boolean isLoggedin = false;
        try{
            Socket socket = new Socket("localhost",5000);
            TestClient ampifyClient = new TestClient(socket);
            Scanner sc = new Scanner(System.in);
            while (!isLoggedin){
                System.out.println("1. Signup\n" +
                        "2. Login");
                int ch = sc.nextInt();
                sc.nextLine();
                switch (ch){
                    case 1:{
                        String email;
                        String passwd;
                        String name;
                        System.out.println("Name: ");
                        name = sc.nextLine();
                        System.out.println("Email: ");
                        email = sc.nextLine();
                        System.out.println("Password: ");
                        passwd = sc.nextLine();
                        Request req = new SignupRequest(name,email,passwd);
                        ampifyClient.sendRequest(socket,req);
                        Response res = (SignupResponse) ampifyClient.getResponse(socket);
                        System.out.println(res);
                        break;
                    }

                    case 2:{
                        String email;
                        String passwd;
                        System.out.println("Email: ");
                        email = sc.nextLine();
                        System.out.println("Password: ");
                        passwd = sc.nextLine();
                        Request req = new LoginRequest(email,passwd);
                        ampifyClient.sendRequest(socket,req);
                        LoginResponse res = (LoginResponse) ampifyClient.getResponse(socket);
                        if(res.getCode() == ResponseCode.SUCCESS) {
                            isLoggedin = true;
                            userId = res.getUserId();
                        }
                        System.out.println(res);
                        userId = res.getUserId();
                        break;
                    }
                    default:
                        System.out.println("Wrong choice.");
                }
            }

            System.out.println("Now user is logged in and has entered the application.");
            Request req;
            while (true){
                //Get the notifications not like this but in separate thread.
                req = new ListInvites(userId);
                ampifyClient.sendRequest(socket,req);
                ListInvitesResponse res = (ListInvitesResponse) ampifyClient.getResponse(socket);
                if(res.getInvites()!=null)
                    res.getInvites().forEach(invite->System.out.println(invite));

                int ch;
                System.out.println("" +
                        "1. Create Group\n" +
                        "2. List Groups\n" +
                        "3. Invite\n" +
                        "4. List Members\n");
                ch = sc.nextInt();
                sc.nextLine();
                switch (ch){
                    case 1:{
                        String GrpName = sc.nextLine();
                        System.out.print("Enter the name of the group : ");
                        req = new CreateGroup(userId,GrpName);
                        ampifyClient.sendRequest(socket,req);
                        CreateGroupResponse res1 = (CreateGroupResponse)ampifyClient.getResponse(socket);
                        System.out.println(res1);
                        break;
                    }
                    case 2:{
                        req = new ListGroups(userId);
                        ampifyClient.sendRequest(socket,req);
                        ListGroupsResponse res2 = (ListGroupsResponse) ampifyClient.getResponse(socket);
                        ArrayList<Group> groups = res2.getGroups();
                        groups.forEach(group -> System.out.println(group));
                    }
                    default:
                        System.out.println("Wrong choice.");
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void sendRequest(Socket socket,Request req) throws IOException{
        oos.writeObject(req);
        oos.flush();
    }
    public Object getResponse(Socket socket) throws IOException, ClassNotFoundException {
        return ooi.readObject();
    }
}