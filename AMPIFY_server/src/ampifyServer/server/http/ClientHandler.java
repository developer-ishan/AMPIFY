package ampifyServer.server.http;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{

    Socket client;
    BufferedReader br;
    DataOutputStream dos;

    public ClientHandler(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        System.out.println("-----------new Client------------\n--------"+client.getInetAddress()+":"+client.getPort());

        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()
                    )
            );
            dos = new DataOutputStream(
                    client.getOutputStream()
            );

            StringBuilder requestBuilder = new StringBuilder();
            String line;
            // Request ends with one empty line (\r\n). Client will send empty
            // line, but imputStream will be still open, we have to read it until one, empty
            // line arrives.
            while (!(line = br.readLine()).isBlank()) {
                System.out.println(line);
                requestBuilder.append(line + "\r\n");
                // In HTTP every new line separator is a Window's new line. \r\n not \n
            }

            String request = requestBuilder.toString();
            // System.out.println(request);

            // Parsing the request
            /*
             * GET / HTTP/1.1 Host: localhost:8080 User-Agent: Mozilla/5.0 (X11; Linux
             * x86_64; rv:81.0) Gecko/20100101 Firefox/81.0 Headers--------------->
             */


            //Parsing the request and headers
            String[] requestLines = request.split("\r\n");
            String[] requestLine = requestLines[0].split(" ");
            String method = requestLine[0];
            String path = requestLine[1];
            String version = requestLines[2];
            String host = requestLines[1].split(" ")[1];

            List<String> headers = new ArrayList<>();
            for (int h = 2; h < requestLines.length; h++) {
                String header = requestLines[h];
                headers.add(header);
            }

            Path filePath = getFilePath(path);
            System.out.println(path);

            if (Files.exists(filePath)) {
                // file exist
                String contentType = getContentType(filePath);
                sendResponse(200,filePath);
            } else {
                // 404
                sendResponse(404,getFilePath("not_found.html"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getFilePath(String path) {
        if("/".equals(path)){
            path = "/index.html";
        }
        return Paths.get("F:\\Projects\\AMPIFY\\AMPIFY_server\\Ampify_Data",path);
    }

    public String getContentType(Path filePath){
        if(filePath.endsWith(".htm") || filePath.endsWith(".html"))
            return "text/html";
        else if(filePath.endsWith(".mp3"))
            return "audio/mpeg";
        else if(filePath.endsWith(".mp4"))
            return "video/mpeg";
        else if(filePath.endsWith(".jpg"))
            return "image/jpeg";
        else if(filePath.endsWith(".png"))
            return "image/png";
        else
            return "text/html";
    }

    private void sendResponse(int statusCode, Path filePath){
        String status = null;
        String serverdetails = "Server: Java HTTPServer";
        String contentLength = null;
        String contentType = "Content-Type: "+getContentType(filePath)+"\r\n";


        if(statusCode == 200)
            status = "HTTP/1.1 200 OK" + "\r\n";
        else
            status = "HTTP/1.1 404 Not Found" + "\r\n";

        try {
            FileInputStream fin = new FileInputStream(filePath.toFile());
            contentLength = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";

            dos.writeBytes(status);
            dos.writeBytes(serverdetails);
            dos.writeBytes(contentType);
            dos.writeBytes(contentLength);
            dos.writeBytes("Connection: close\r\n");
            dos.writeBytes("\r\n");

            // send the file
            byte[] buffer = new byte[1024];
            int bytes;

            while ((bytes = fin.read(buffer)) != -1 ) {
                dos.write(buffer, 0, bytes);
                dos.flush();
            }

            fin.close();
            dos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
