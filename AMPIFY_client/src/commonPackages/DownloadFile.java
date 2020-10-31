package commonPackages;
import commonPackages.models.Song;

import java.io.*;
import java.net.URL;

public class DownloadFile {
    public final static int SOCKET_PORT = 8081;      //port num
    public final static String SERVER = "127.0.0.1"; // localhost

    static File dir=new File("D:\\Ampify");
    static boolean FolderCreated=dir.mkdir();
    public static String FILE_TO_RECEIVED ;  // change it

    public final static int FILE_SIZE = 6022386; // file size temporary hard coded

    public static void download(Song song) throws IOException {
        if(FolderCreated){
            System.out.println("created");
        }
        URL url=new URL("http://localhost:8081/songs/"+song.getId()+".mp3");
        File file=new File("D:/Ampify/"+song.getId()+".mp3");
        try {
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }
            InputStream input = url.openStream();
            FileOutputStream output = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            input.close();
            output.close();
            System.out.println("File '" + file + "' downloaded successfully!");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

}