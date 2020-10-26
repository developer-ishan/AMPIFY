package ampifyServer.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

public class SocketServer {

    static Vector<String> avalUsers;
    public static void main(String[] args) throws IOException {
        //List of available users
        avalUsers = new Vector<>();
        //Server running at port 5000
        int PORT = 5000;

        ServerSocket serverSocket = null;
        Connection con = null;
        try{
            //start the server
            serverSocket = new ServerSocket(PORT);
            //connect to mysql server
            con = dbConnection.connect();
        } catch (SQLException throwables) {
            System.err.println("Cannot connect to SQL database.");
            return;
        } catch (IOException e) {
            System.err.println("Cannot Start Socket Server");
            e.printStackTrace();
            return;
        }



        //Running infinite loop to listen to new client connections
        while (true)
        {
            Socket socket = null;
            try{
                System.out.println("Available users are:- ");
                for (String userId: avalUsers) {
                    System.out.println(userId);
                }

                socket = serverSocket.accept();
                System.out.println(socket);
                System.out.println(socket.toString()+" is trying to login.");

                //Obtain the Object output and input streams
                ObjectInputStream ois = new ObjectInputStream(
                        socket.getInputStream()
                );
                ObjectOutputStream oos = new ObjectOutputStream(
                        socket.getOutputStream()
                );

                System.out.println("Assigning new thread for this client");

                Thread clientThread = new Thread(new ClientHandler(socket, ois, oos,con));
                clientThread.start();

            } catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}