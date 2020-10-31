package components.controllers.cards;

import commonPackages.models.Group;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupCard extends MenuItem implements Initializable{
    @FXML
    private ImageView groupImage;

    @FXML
    private Button name;

    @FXML
    void openGroup(ActionEvent event) {

    }

    private Group group;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(group.getName());
    }
}
