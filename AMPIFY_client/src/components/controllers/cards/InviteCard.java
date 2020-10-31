package components.controllers.cards;

import commonPackages.models.Group;
import commonPackages.requests.Request;
import commonPackages.requests.user.AcceptInvite;
import commonPackages.requests.user.DeclineInvite;
import commonPackages.responses.ResponseCode;
import commonPackages.responses.user.AcceptInviteResponse;
import commonPackages.responses.user.DeclineInviteResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import socket.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class InviteCard implements Initializable {
    private Client client;
    private Group group;
    private VBox groupsList;
    @FXML
    private Label name;

    @FXML
    private Button accept;

    @FXML
    private Button decline;

    public InviteCard(Client client, Group group, VBox groupsList){
        this.client = client;
        this.group = group;
        this.groupsList = groupsList;
    }
    @FXML
    void acceptListener(ActionEvent event) {
        AcceptInvite req = new AcceptInvite(client.getToken(),group.getId());
        client.sendRequest(req);
        AcceptInviteResponse res = (AcceptInviteResponse) client.getResponse();

        if(res.getCode()!= ResponseCode.SUCCESS){

        } else {

        }
    }

    @FXML
    void declineListener(ActionEvent event) {
        DeclineInvite req = new DeclineInvite(client.getToken(),group.getId());
        client.sendRequest(req);
        DeclineInviteResponse res = (DeclineInviteResponse) client.getResponse();

        if(res.getCode()!= ResponseCode.SUCCESS){

        } else {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.name.setText(group.getName());
    }
}
