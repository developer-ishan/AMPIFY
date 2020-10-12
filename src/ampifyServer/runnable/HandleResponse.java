package ampifyServer.runnable;

import ampifyServer.responses.Response;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HandleResponse implements Runnable{
    Socket socket;
    Response response;
    public HandleResponse(Socket socket, Response response){
        this.socket=socket;
        this.response=response;
    }
    @Override
    public void run() {

        try {
            ObjectOutputStream outputStream=
                    new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(response);
            outputStream.flush();
            System.out.println("Response Send Successfully ::::::  "+response.toString());
        } catch (IOException e) {
            System.out.println("Response Send Unsuccessfully ::::::  "+response.toString()
                    +"\nError Has Occured :::::  "+e);
            e.printStackTrace();
        }
    }
}
