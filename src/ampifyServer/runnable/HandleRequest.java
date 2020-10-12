package ampifyServer.runnable;

import ampifyServer.requests.Request;
import ampifyServer.requests.auth.SignupRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HandleRequest implements Runnable {
    Socket socket;
    Request request;

    public HandleRequest(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
            try {
                ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
                request=(Request)inputStream.readObject();
                System.out.println("Request Recieved Succesfully ::::::  "+request);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No Request Is Recieved \nError Has Occured :::  "+e);
                e.printStackTrace();
            }
    }
    public Request getRequest() {
        return request;
    }
}
