import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import socket.Client;

import java.net.ConnectException;
import java.net.Socket;

public class Ampify extends Application {
    private Client client;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ampify");

        Parent landing  = FXMLLoader.load(getClass().getResource("landing.fxml"));
        primaryStage.setScene(new Scene(landing,1000,900));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}