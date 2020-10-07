package Networking;

import ampifyServer.Song;

import java.io.File;

public class FileExist {
   public static File exist(Song song)
   {
       //file path on native machine
       String filepath="C:\\Users\\hp\\Desktop\\"+song.getSongname()+".wav";
       File file=new File(filepath);
       if(file.exists()&&file.isFile())
       {
           return file;
       }
       throw new IllegalArgumentException("Either file does not exist or is not a file>---->>" + filepath);
   }
}
