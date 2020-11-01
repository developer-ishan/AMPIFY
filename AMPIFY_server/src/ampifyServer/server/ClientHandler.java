package ampifyServer.server;

import ampifyServer.requestHandler.JWebToken;
import ampifyServer.requestHandler.RequestHandler;
import ampifyServer.requestHandler.UserRequestsHandler;
import commonPackages.requests.GetNotifications;
import commonPackages.requests.Request;
import commonPackages.responses.Response;
import commonPackages.responses.auth.InvalidToken;
import commonPackages.responses.auth.LoginResponse;
import commonPackages.responses.auth.SignupResponse;
import commonPackages.responses.user.ListInvitesResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

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
        while (true){
            try {
                // get the request
                Request req = (Request) this.getRequest();
                System.out.println(req);
                // if the request is of notifications
                // then this thread needs to be notification thread
                if(req instanceof GetNotifications){
                    System.out.println("Handler converted to notification handler.");
                    ListInvitesResponse invitesR;
                    userId = ((GetNotifications) req).getUserId();

                    // send for the first time
                    //INVITES
                    ListInvitesResponse invitesResponse = UserRequestsHandler.getInvites(userId,con);
                    this.sendReponse(invitesResponse);

                    while (true){

                        // refresh only if the notification is different from that of last one
                        ListInvitesResponse invitesResponse_new = UserRequestsHandler.getInvites(userId,con);
                        if(invitesResponse_new!=null && !invitesResponse_new.toString().equals(invitesResponse.toString())){
                            invitesResponse = invitesResponse_new;
                            this.sendReponse(invitesResponse);
                        }
                    }

                }
                // process the request
                Response res = RequestHandler.getResponse(req,con);
                // send the response
                this.sendReponse(res);
                System.out.println(res);

                if(res instanceof LoginResponse){
                    LoginResponse lgres = (LoginResponse)res;
                    userId = lgres.getUser().getId();
                    long index = SocketServer.avalUsers.indexOf(userId);
                    if(index==-1)
                        SocketServer.avalUsers.add(userId);
                } else if(res instanceof SignupResponse){
                    continue;
                } else {
                    JWebToken token = new JWebToken(
                            req.getToken()
                    );
                    if(token.isValid()){
                        userId = token.getSubject();
                        long index = SocketServer.avalUsers.indexOf(userId);
                        if(index==-1)
                            SocketServer.avalUsers.add(userId);
                        SocketServer.avalUsers.add(userId);
                    } else {
                        this.sendReponse(new InvalidToken());
                        continue;
                    }
                }
            }
            catch (IOException e){
                long index = SocketServer.avalUsers.indexOf(userId);
                if(index!=-1)
                    SocketServer.avalUsers.remove(index);
                System.out.println("Connection link is broken.");
                return;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
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