package ampifyServer.server;

import ampifyServer.requestHandler.JWebToken;
import ampifyServer.requestHandler.RequestHandler;
import ampifyServer.requestHandler.UserRequestsHandler;
import commonPackages.models.User;
import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.auth.InvalidToken;
import commonPackages.responses.auth.LoginResponse;
import commonPackages.responses.auth.SignupResponse;
import commonPackages.responses.user.ListInvitesResponse;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final Connection con;
    private String userId;

    public ClientHandler(
            Socket socket,
            ObjectInputStream ois,
            ObjectOutputStream oos,
            Connection con
    ){
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
        this.con = con;
        userId = null;
    }
    @Override
    public void run() {
        long b1=-1,b2=-1;
        while (true){
            try {
                Request req = (Request) this.getRequest();
                if(!(req instanceof LoginRequest || req instanceof SignupRequest) && userId == null){
                    JWebToken token = new JWebToken(
                            req.getToken()
                    );
                    if(token.isValid()){
                        userId = token.getSubject();
                        SocketServer.avalUsers.add(userId);
                    } else {
                        this.sendReponse(new InvalidToken());
                        continue;
                    }
                }

                Response res = RequestHandler.getResponse(req,con);
                this.sendReponse(res);
                System.out.println(res);
            }
            catch (Exception e){
                if(b1==-1)
                    b1 = new Date().getTime();
                else {
                    long index = SocketServer.avalUsers.indexOf(userId);
                    if(index!=-1)
                        SocketServer.avalUsers.remove(index);
                    // user will be given 10 sec timeout if connection is broken after
                    // which he will be declared inactive
                    if(new Date().getTime() - b1 >= 10000) {
                        System.out.println("User signing off.");
                        return;
                    }
                }
                System.out.println("Connection link is broken.");
            }
        }
    }
    public void sendReponse(Response res) throws IOException {
        oos.writeObject(res);
        oos.flush();
    }
    public Object getRequest() throws IOException, ClassNotFoundException {
        Object req = ois.readObject();
        return req;
    }
}