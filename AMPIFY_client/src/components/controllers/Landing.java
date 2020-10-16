package components.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class Landing {

    public Button loginBtn, signupBtn, offlineBtn;

    public void loginListener(ActionEvent actionEvent){
        System.out.println("Login Clicked.");
    }

    public void signupListener(ActionEvent actionEvent){
        Stage stage = (Stage) signupBtn.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/auth/signup.fxml"));
        } catch (IOException e){
            e.printStackTrace();
        }
        stage.setScene(new Scene(root,500,400));
    }

    public void goOfflineListener(ActionEvent actionEvent){
        System.out.println("Go offline Clicked.");
    }
}
