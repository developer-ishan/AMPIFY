package components.controllers;

import commonPackages.requests.Request;
import commonPackages.requests.user.UserDetails;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.user.UserDetailsResponse;
import components.controllers.user.Home;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import socket.Client;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class Landing {

    public Button loginBtn, signupBtn, offlineBtn;

    public void loginListener(ActionEvent actionEvent){
        Stage stage = (Stage) loginBtn.getScene().getWindow();


        String tokenPath = System.getProperty("user.dir");
        File token = new File(tokenPath + "\\user_data\\token");

        //TODO if token already exist get the user details
        if(token.exists()){
            try {
                Socket socket = new Socket("localhost",5000);
                Client client = new Client(socket);

                Request req = new UserDetails(client.getToken());
                client.sendRequest(req);
                UserDetailsResponse res = (UserDetailsResponse) client.getResponse();
                System.out.println(res);
                if(res.getCode() == ResponseCode.SUCCESS){

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/home.fxml"));

                    Home home = new Home();
                    home.setClient(client);
                    home.setUser(res.getUser());
                    loader.setController(home);

                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    stage.setWidth(1920);
                    stage.setHeight(1080);
                    stage.setScene(scene);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Parent root = null;

            try {
                root = FXMLLoader.load(getClass().getResource("/auth/login.fxml"));
                stage.setScene(new Scene(root, 450, 350));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void signupListener(ActionEvent actionEvent){
        Stage stage = (Stage) signupBtn.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/auth/signup.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }
        stage.setScene(new Scene(root,450,300));
    }

    public void goOfflineListener(ActionEvent actionEvent){
        System.out.println("Go offline Clicked.");
    }
}
