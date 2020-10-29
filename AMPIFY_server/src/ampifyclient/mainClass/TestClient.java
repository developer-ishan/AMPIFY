package ampifyclient.mainClass;

import commonPackages.models.Song;
import commonPackages.requests.Request;
import commonPackages.requests.song.ListSongs;
import commonPackages.responses.song.ListSongsResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class TestClient {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ooi;
    public TestClient(Socket socket) throws IOException {
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ooi = new ObjectInputStream(socket.getInputStream());
    }
    public static void main(String[] args) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1ZDdlMmUxMS1jYTFjLTRhODAtYWJkOS1lNDkzYmZiZGJlODgiLCJhdWQiOlsidXNlciJdLCJpc3MiOiJBTVBJRlkiLCJleHAiOjE2MDY1OTg1NzEsImlhdCI6MTYwNDAwNjU3MSwianRpIjoiYmU3YzE1OTgtNzlhNy00YjIwLTg1ZWMtZWI2ZmQ1ZmE0ZWE3In0.aCOCZXI6fiKhRkFszzeH7N0qUOVAXhUTZ_oxriQt7lY";
        try{
            Socket socket = new Socket("localhost",5000);
            TestClient ampifyClient = new TestClient(socket);
            Request req = new ListSongs(token);
            ampifyClient.sendRequest(socket,req);
            ListSongsResponse res = (ListSongsResponse) ampifyClient.getResponse(socket);
            ArrayList<Song> songArrayList = res.getSongs();
            songArrayList.forEach((song)->{
                System.out.println(song);
            });
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void sendRequest(Socket socket,Request req) throws IOException{
        oos.writeObject(req);
        oos.flush();
    }
    public Object getResponse(Socket socket) throws IOException, ClassNotFoundException {
        return ooi.readObject();
    }
}