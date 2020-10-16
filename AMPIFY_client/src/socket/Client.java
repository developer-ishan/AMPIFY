package socket;

import commonPackages.requests.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ooi;
    public Client(Socket socket) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ooi = new ObjectInputStream(socket.getInputStream());
    }

    public void sendRequest(Request req) throws IOException{
        oos.writeObject(req);
        oos.flush();
    }
    public Object getResponse() throws IOException, ClassNotFoundException {
        return ooi.readObject();
    }
}
