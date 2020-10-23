package ampifyServer.server.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        System.out.println("TCPServer listening for client on port 8080");

        while (true){
            Socket client = serverSocket.accept();
            Thread clientThread = new Thread(new ClientHandler(client));
            clientThread.start();
        }
    }
}
