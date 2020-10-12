package ampifyServer.SocketServer;

import ampifyServer.requests.Request;
import ampifyServer.responses.Response;
import ampifyServer.runnable.HandleRequest;
import ampifyServer.runnable.HandleResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    static Request request;
    static ServerSocket serverSocket;
    static Response response;
    public static Request getRequestObject() {
        try{
            serverSocket = new ServerSocket(5555);
            Socket socket = serverSocket.accept();
            HandleRequest handleRequest =new HandleRequest(socket);
            Thread thread=new Thread(handleRequest);
            thread.start();
            request= handleRequest.getRequest();
            System.out.println("Request Recieved Succesfully ::::::  "+request);
        } catch (IOException e) {
            request=null;
            System.out.println("No Request Is Recieved \nError Has Occured :::  "+e);
        }
        return request;
    }
    public static void SendResponseObject(Response response) {
        try {
            serverSocket=new ServerSocket(5555);
            Socket socket=serverSocket.accept();
            Thread thread=new Thread(new HandleResponse(socket,response));
            thread.start();
            System.out.println("Response Send Successfully ::::::  "+response.toString());
        } catch (IOException e) {
            System.out.println("Response Send Unsuccessfully ::::::  "+response.toString()
                    +"\nError Has Occured :::::  "+e);
            e.printStackTrace();
        }
    }

}

