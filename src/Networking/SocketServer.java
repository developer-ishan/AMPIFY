package Networking;

import ampifyServer.Song;
import ampifyServer.requests.Request;
import ampifyServer.responses.Response;
import ampifyServer.responses.ResponseCode;
import com.sun.net.httpserver.Authenticator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

public class SocketServer{
    public static Request getSongObject() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(5555);
                Socket socket = serverSocket.accept();
                Thread thread=new Thread(new HandleClient(socket));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Response sendSongtoClient(Song song)
    {
        //provide filepath here
        String path= ""+song.getSongname()+".wav";
        System.out.println("server: " + path);
            try {
                ServerSocket serverSocker = new ServerSocket(5555);
                FileInputStream inputStream = new FileInputStream(path);
                if (serverSocker.isBound()) {
                Socket client = serverSocker.accept();
                OutputStream clientOutputStream = client.getOutputStream();
                byte[] buffer = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer)) != -1)
                    clientOutputStream.write(buffer, 0, count);
            }
                else
                {
                    System.out.println("Server is not bounded to any address ");
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("server: shutdown");
        return new Response(ResponseCode.SUCCESS,"Song send successfully");
    }
}