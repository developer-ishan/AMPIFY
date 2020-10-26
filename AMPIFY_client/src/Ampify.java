import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ampify extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Ampify");

        Parent landing  = FXMLLoader.load(getClass().getResource("landing.fxml"));
        primaryStage.setScene(new Scene(landing,300,400));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}