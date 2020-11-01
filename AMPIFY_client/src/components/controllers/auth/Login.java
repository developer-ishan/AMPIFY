package components.controllers.auth;

import commonPackages.responses.ResponseCode;
import commonPackages.requests.Request;
import commonPackages.requests.auth.LoginRequest;
import commonPackages.responses.Response;
import commonPackages.responses.auth.LoginResponse;
import commonPackages.responses.user.UserDetailsResponse;
import components.controllers.user.Home;
import components.controllers.user.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import socket.Client;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Login {
    @FXML
    private TextField email;
    @FXML
    private PasswordField passwd;
    @FXML
    private Button loginBtn;
    @FXML
    private  Button backBtn;

    public void toLanding(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage) backBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/landing.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root, 300, 400));
    }

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
        Client client = null;
        try {
            // connect to server
            socket = new Socket("localhost",5000);
            client = new Client(socket);
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
                try{
                    String tokenPath = System.getProperty("user.dir");
                    File token = new File(tokenPath + "\\user_data\\token");
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(token));
                    dos.writeBytes(res.getToken());
                    dos.close();
                } catch (IOException e){
                    System.out.println("The token cannot be stored try again.");
                }

                confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setHeaderText("Successfully Logged In!!");
                confirmAlert.show();

                // send the user to the home page
                Stage stage = (Stage) loginBtn.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/home.fxml"));

                Home home = new Home();
                home.setClient(client);
                home.setUser(res.getUser());
                loader.setController(home);

                Parent root = loader.load();
                Scene scene = new Scene(root);

                int width = 1920;
                int height = 1080;
                stage.setWidth(width);
                stage.setHeight(height);
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX((screenBounds.getWidth() - width) / 2);
                stage.setY((screenBounds.getHeight() - height) / 2);
                stage.setScene(scene);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
