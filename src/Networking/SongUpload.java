package Networking;

import ampifyServer.Song;
import ampifyServer.responses.Response;
import runnable.server.HandleSongUpload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SongUpload {
    //this method will send song to the client who has requested the song
    public static Response uploadtoClient(Song song) {
        //provide filepath here
        String path= ""+song.getSongname()+".wav";
        System.out.println("server: " + path);
        File file=new File(path);
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        ServerSocket serverSocket;
        Socket socket;
        try {
            serverSocket=new ServerSocket(5566);
            while (true) {
                System.out.println("Waiting...");
                socket=serverSocket.accept();
                System.out.println("CONNECTED :" +socket);
               Thread thread=new Thread(new HandleSongUpload(file,socket));
               thread.start();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
