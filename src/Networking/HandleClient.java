package Networking;

import ampifyServer.Song;
import ampifyServer.responses.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HandleClient implements Runnable {
    Socket socket;
    ObjectInputStream inputStream;
    public HandleClient(Socket socket) {
        this.socket=socket;
        try {
            inputStream=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            try {
                Song song=(Song) inputStream.readObject();
                System.out.println(song);
                Response res=SocketServer.sendSongtoClient(song);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
}
