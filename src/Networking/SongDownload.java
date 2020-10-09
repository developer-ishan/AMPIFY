package Networking;

import ampifyServer.Song;
import ampifyServer.responses.Response;
import runnable.client.HandleSongDownload;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class SongDownload {
    static Socket socket;
    public static final String HOSTNAME="localhost";
    public static final int PORT=5566; //download port

    public static Response download(Song song)
    {
        //craeating directory if not exist
        String dirpath="C:\\ampify";
        File dir=new File(dirpath);
        boolean bool=dir.mkdir();
        //after creating directory adding song path to dir
        if(dir.isDirectory())
        {
           String filepath=dirpath+"\\"+song.getSongname()+"("+song.getArtist()+")";
           int i=1;
           while(new File(filepath).exists())
               filepath=filepath+"copy("+(i++)+")";
            try {
                socket=new Socket(HOSTNAME,PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connecting.....");
            Thread thread=new Thread(new HandleSongDownload(socket,filepath));
            thread.start();

        }
        return null;
    }
}
