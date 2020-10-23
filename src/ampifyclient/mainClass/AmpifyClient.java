package AMPIFY_server.src.ampifyclient.mainClass;


import AMPIFY_server.src.commonPackages.requests.Request;
import AMPIFY_server.src.commonPackages.requests.auth.LoginRequest;
import AMPIFY_server.src.commonPackages.requests.auth.SignupRequest;
import AMPIFY_server.src.commonPackages.responses.Response;
import AMPIFY_server.src.commonPackages.responses.auth.LoginResponse;
import AMPIFY_server.src.commonPackages.responses.auth.SignupResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class AmpifyClient{
    private final ObjectOutputStream oos;
    private final ObjectInputStream ooi;
    public AmpifyClient(Socket socket) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ooi = new ObjectInputStream(socket.getInputStream());
    }
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("localhost",5000);
            AmpifyClient ampifyClient = new AmpifyClient(socket);
            Scanner sc = new Scanner(System.in);

            while (true){

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
                        Response res = (LoginResponse) ampifyClient.getResponse(socket);
                        System.out.println(res);
                        break;
                    }
                    default:
                        System.out.println("Wrong choice.");
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
