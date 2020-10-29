package socket;

import commonPackages.requests.Request;

import java.io.*;
import java.net.Socket;

public class Client {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ooi;
    private final Socket socket;
    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ooi = new ObjectInputStream(socket.getInputStream());
    }

    public void sendRequest(Request req){
        try {
            oos.writeObject(req);
            oos.flush();
        } catch (IOException e) {
            System.out.println("Server Down.");
            e.printStackTrace();
        }
    }
    public Object getResponse(){
        try {
            return ooi.readObject();
        } catch (IOException e) {
            System.out.println("Server Down.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid Response.");
            e.printStackTrace();
        }
        return null;
    }
    public String getToken(){
        try{
            String tokenPath = System.getProperty("user.dir");
            File token = new File(tokenPath + "\\user_data\\token");
            BufferedReader br = new BufferedReader(new FileReader(token));
            String tokenVal =  br.readLine();
            if(tokenVal == null)
                throw new IOException("Token not found login first.");
            else
                return tokenVal;
        } catch (IOException e){
            System.out.println("The token is not stored try again.");
        }
        return null;
    }
    @Override
    public String toString() {
        return "Client{" +
                "socket=" + socket +
                '}';
    }
}
