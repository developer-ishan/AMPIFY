package ampifyclient.runnable;

import ampifyclient.responses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HandleResponse implements Runnable {
    Socket socket;
    Response response;
    public HandleResponse(Socket socket)
    {
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
            response=(Response) inputStream.readObject();
            System.out.println("Response Recieved Succesfully ::::::  "+response);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No Response Is Recieved \nError Has Occured :::  "+e);
            e.printStackTrace();
        }
    }
    public Response getResponse() {
        return response;
    }
}
