package components.controllers.auth;

import commonPackages.responses.ResponseCode;
import commonPackages.requests.Request;
import commonPackages.requests.auth.SignupRequest;
import commonPackages.responses.Response;
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

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup {
    public TextField name, email;
    public PasswordField password;
    public Button signupBtn;
    public Button backBtn;

    public void toLanding(ActionEvent actionEvent) throws Exception {

        Stage stage = (Stage) backBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/landing.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root, 300, 400));
    }

    public void signup(ActionEvent actionEvent){
        String name = this.name.getText();
        String email = this.email.getText();
        String password = this.password.getText();
        String pattern = "^(?=.*[0-9])"
                        + "(?=.*[a-z])(?=.*[A-Z])"
                        + "(?=.*[@#$%^&+=-_])"
                        + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        Alert errAlert  = null;
        Alert confirmAlert = null;

        if(name.length() < 1 || password.length() < 1 || email.length() < 1) {
            errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid Input");
            errAlert.setContentText("Fields Should Not Be Kept Empty!!");
            errAlert.show();
            return ;
        }

        int ind1 = email.indexOf('@'), ind2 = email.indexOf('.');
        if(!((ind1 > 1 && ind1 < email.length() - 3) || (ind2 > 0 && ind2 < email.length() - 2))) {
            errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid Email");
            errAlert.setContentText("Please Enter Your Valid Email!!");
            this.email.setText("");
            errAlert.show();
            return ;
        }

        if(password.length() < 8 || !m.matches()) {
            errAlert = new Alert(Alert.AlertType.ERROR);
            errAlert.setHeaderText("Invalid Password");
            errAlert.setContentText("Password Should Be 8-20 Characters, Containing Alphabets, Numbers & Symbols!!");
            this.password.setText("");
            errAlert.show();
            return ;
        }

        Request req = new SignupRequest(name,email,password);

        System.out.println(String.format("%s\n%s\n%s\n",name,email,password));
        Socket socket = null;

        try {
            socket = new Socket("localhost",5000);
            Client client = new Client(socket);
            System.out.println("before");
            client.sendRequest(req);
            System.out.println("after");
            Response res = (Response) client.getResponse();
            System.out.println(res);

            if(res.getCode() != ResponseCode.SUCCESS) {
                errAlert = new Alert(Alert.AlertType.ERROR);
                errAlert.setHeaderText("Invalid Login");
                errAlert.setContentText(res.getMessage());
                this.name.setText("");
                this.password.setText("");
                this.email.setText("");
                errAlert.show();
                return ;
            }else {
                confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setHeaderText("Successfully Signed In!!");
                confirmAlert.show();
            }

//            socket.close();
            //Get him to login page create a new client
            //Once logged in propagate this client object through out the application
        } catch (IOException | ClassNotFoundException e) {
            //This means server is down
            //redirect to landing page or ask to go offline
            e.printStackTrace();
        }
    }
}
