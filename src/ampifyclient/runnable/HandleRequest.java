package ampifyclient.runnable;

import ampifyclient.requests.Request;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandleRequest implements Runnable{
    Socket socket;
    Request request;
    public HandleRequest(Socket socket, Request request)
    {
        this.socket=socket;
        this.request=request;
    }
    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println("Request Send Successfully :::: "+request.toString());
        } catch (IOException e) {
            System.out.print("Request Send Unsuccessfully :::: "+request+
                    "\nError has Occured :::: "+e);
            e.printStackTrace();
        }
    }
}
