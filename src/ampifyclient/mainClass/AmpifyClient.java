package ampifyclient.mainClass;

import ampifyclient.SocketClient.SocketClient;
import ampifyclient.requests.Request;
import ampifyclient.requests.auth.LoginRequest;
import ampifyclient.requests.auth.SignupRequest;
import ampifyclient.responses.Response;


public class AmpifyClient implements Runnable{

    public static void main(String[] args) {
        Thread thread=new Thread(new AmpifyClient());
        thread.start();
    }
    @Override
    public void run() {
        Request request=new LoginRequest("arun@mnnit.ac.in","a");
        //Request request=new SignupRequest("pirate","pirate@mnnit.ac.in","pirate");
        SocketClient.sendRequestObject(request);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Response response=SocketClient.getResponseObject();
    }
}
