package AMPIFY_server.src.ampifyServer.server;

import AMPIFY_server.src.ampifyServer.requestHandler.UserRequestsHandler;
import AMPIFY_server.src.commonPackages.models.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class SocketServer {

    public static void main(String[] args) throws IOException, SQLException {
        //Server is listening on port 5000
        ServerSocket serverSocket = new ServerSocket(5000);
        Connection con = dbConnection.connect();
        System.out.println("Server Started at port 5000");
        HashMap<String ,Boolean> avalUsers;

        //Running infinite loop to listen to new client connections
        while (true)
        {
            Socket socket = null;
            try{
                avalUsers = UserRequestsHandler.getAvalUsers(con);
                System.out.println("Available users are:- ");
                avalUsers.forEach((k,v) -> {
                    System.out.println(k+" : "+v);
                });

                socket = serverSocket.accept();
                System.out.println(socket.toString()+" is trying to login.");

                //Obtain the Object output and input streams
                ObjectInputStream ois = new ObjectInputStream(
                        socket.getInputStream()
                );
                ObjectOutputStream oos = new ObjectOutputStream(
                        socket.getOutputStream()
                );

                System.out.println("Assigning new thread for this client");

                Thread clientThread = new Thread(new ClientHandler(socket, ois, oos, avalUsers,con));
                clientThread.start();

            } catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

