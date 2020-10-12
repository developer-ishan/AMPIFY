package ampifyServer.SocketServer;

import commonPackages.requests.Request;
import commonPackages.responses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    static Request request;
    static ServerSocket serverSocket;
    public static Request getRequestObject() {
        try{
            serverSocket = new ServerSocket(5555);
            Socket socket = serverSocket.accept();
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());
            request=(Request)inputStream.readObject();
            System.out.println("Request Recieved Succesfully ::::::  "+request);
        } catch (IOException e) {
            request=null;
            System.out.println("No Request Is Recieved \nError Has Occured :::  "+e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;
    }
    public static void SendResponseObject(Response response) {
        try {
            Socket socket=serverSocket.accept();
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

