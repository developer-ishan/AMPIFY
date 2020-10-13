package ampifyclient.mainClass;


import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;
import commonPackages.responses.auth.SignupResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

            Request req = new SignupRequest("test3","test3@gmail.com","test");
            ampifyClient.sendRequest(socket,req);
            Response res = (SignupResponse) ampifyClient.getResponse(socket);

            System.out.println(res);
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
