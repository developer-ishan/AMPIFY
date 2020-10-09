package runnable.server;

import java.io.*;
import java.net.Socket;

public class HandleSongUpload implements Runnable{
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    OutputStream outputStream;
    Socket socket;
    File file;
    public HandleSongUpload(File file, Socket socket)
    {
        this.file=file;
        this.socket=socket;
    }
    @Override
    public void run() {
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedInputStream=new BufferedInputStream(fileInputStream);
        byte[] file_byte=new byte[(int) file.length()];
        try {
            bufferedInputStream.read(file_byte,0,file_byte.length);
            outputStream=socket.getOutputStream();
            System.out.println("Sending file to client : : :  "+file.getName());
            outputStream.write(file_byte,0, file_byte.length);
            outputStream.flush();
            System.out.println("_____________"+file.getName()+" send__________");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null)
                    outputStream.close();
                if (bufferedInputStream!=null)
                    bufferedInputStream.close();
                if (socket!=null)
                    socket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
