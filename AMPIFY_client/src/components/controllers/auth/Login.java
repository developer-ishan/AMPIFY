package components.controllers.auth;

import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.ResponseCode;
import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.responses.Response;
import commonPackages.responses.auth.LoginResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import socket.Client;

import javax.print.attribute.standard.RequestingUserName;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Login {
    public TextField email;
    public PasswordField passwd;
    public Button loginBtn;

    public void login(ActionEvent actionEvent) {
        String email = this.email.getText();
        String passwd = this.passwd.getText();
        Alert errAlert  = null;
        Alert confirmAlert = null;

        if(email.length() < 1 || passwd.length() < 1) {
            errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid Input");
            errAlert.setContentText("Fields Should Not Be Kept Empty!!");
            errAlert.show();
            return ;
        }

        int ind1 = email.indexOf('@'), ind2 = email.indexOf('.');
        if(!((ind1 > 1 && ind1 < email.length() - 3) && (ind2 > 0 && ind2 < email.length() - 2))) {
            errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid Email");
            errAlert.setContentText("Please Enter Your Valid Email!!");
            this.email.setText("");
            errAlert.show();
            return ;
        }

        if(passwd.length() < 8) {
            errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid Password");
            errAlert.setContentText("Password Should Be 8-20 Characters!!");
            this.passwd.setText("");
            errAlert.show();
            return ;
        }

        Request req = new LoginRequest(email, passwd);
        System.out.println(String.format("%s\n%s\n",email,passwd));
        Socket socket = null;

        try {
            // connect to server
            socket = new Socket("localhost",5000);
            Client client = new Client(socket);
            //send the login request
            client.sendRequest(req);
            //get the response
            LoginResponse res = (LoginResponse) client.getResponse();
            System.out.println(res);

            if(res.getCode() != ResponseCode.SUCCESS) {
                errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setHeaderText("Invalid Login");
                errAlert.setContentText(res.getMessage());
                this.passwd.setText("");
                this.email.setText("");
                errAlert.show();
                return ;
            }
            else {
                // the login is successful
                // extract the jwt token and save locally
                File token = new File("F:\\Projects\\AMPIFY\\AMPIFY_client\\user_data\\token");
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(token));
                dos.writeBytes(res.getToken());
                confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setHeaderText("Successfully Logged In!!");
                confirmAlert.show();

                // send the user to the home page
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource("/user/home.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(new Scene(root, 450, 350));
            }

            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
