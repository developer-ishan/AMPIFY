package runnable.client;

import java.io.*;
import java.net.Socket;

public class HandleSongDownload implements Runnable{

    public static final int FILESIZE=1000000000;
    Socket socket;
    String filepath;
    int byteread=0,currentbyte=0;
    BufferedOutputStream bufferedOutputStream;
    FileOutputStream fileOutputStream;
    InputStream inputStream;
    public HandleSongDownload(Socket socket, String filepath)
    {
        this.socket=socket;
        this.filepath =filepath;
    }
    @Override
    public void run() {

        byte[] filedata=new byte[FILESIZE];
        try {
            inputStream=socket.getInputStream();
            fileOutputStream=new FileOutputStream(filepath);
            bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
            byteread = inputStream.read(filedata, 0, filedata.length);
            currentbyte=byteread;
            do {
                byteread=inputStream.read(filedata,currentbyte, filedata.length-currentbyte);
                if(byteread>-1)
                    currentbyte+=byteread;

            }while (byteread>-1);
            bufferedOutputStream.write(filedata,0,filedata.length);
            bufferedOutputStream.flush();
            System.out.println("_____FILE DOWNLOADED_____:::::"+filepath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (bufferedOutputStream!=null)
                    bufferedOutputStream.close();
                if (socket!=null)
                    socket.close();
            }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

}