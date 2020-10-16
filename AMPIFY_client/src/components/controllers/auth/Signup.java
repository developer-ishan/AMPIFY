package components.controllers.auth;

import commonPackages.requests.Request;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import socket.Client;

import java.io.IOException;
import java.net.Socket;

public class Signup {
    public TextField name, email;
    public PasswordField password;
    public Button signupBtn;

    public void signup(ActionEvent actionEvent){
        String name = this.name.getText();
        String email = this.email.getText();
        String password = this.password.getText();

        Request req = new SignupRequest(name,email,password);

        System.out.println(String.format("%s\n%s\n%s\n",name,email,password));
        Socket socket = null;

        try {
            socket = new Socket("localhost",5000);
            Client client = new Client(socket);
            client.sendRequest(req);
            Response res = (Response) client.getResponse();
            System.out.println(res);
            socket.close();
            //Get him to login page create a new client
            //Once logged in propagate this client object through out the application
        } catch (IOException | ClassNotFoundException e) {
            //This means server is down
            //redirect to landing page or ask to go offline
            e.printStackTrace();
        }
    }
}
