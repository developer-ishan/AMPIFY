package components.controllers.user;

import commonPackages.models.Group;
import commonPackages.requests.GetNotifications;
import commonPackages.responses.Response;
import commonPackages.responses.group.InviteResponse;
import commonPackages.responses.user.ListInvitesResponse;
import components.controllers.cards.GroupCard;
import components.controllers.cards.InviteCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import socket.Client;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Notifications implements Runnable{

    private String userId;
    private Socket socket;
    private Client notifClient;
    private Client client;
    private VBox invitesList;
    private VBox groupsList;
    public Notifications(Client client,String userId,VBox invitesList, VBox groupsList){
        this.userId = userId;
        this.invitesList = invitesList;
        this.groupsList = groupsList;
    }
    @Override
    public void run() {
        System.out.println("Initializing Notifications Thread");
        try {
            socket = new Socket("localhost",5000);
            notifClient = new Client(socket);
            notifClient.sendRequest(new GetNotifications(userId));
            while (true){
                Response res = (Response) notifClient.getNotification();
                if(res instanceof ListInvitesResponse){
                    System.out.println(res);
                    ArrayList<Group> invites = ((ListInvitesResponse) res).getInvites();
                    invites.forEach(invite -> {
                        System.out.println(invite);
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards/inviteCard.fxml"));
                            InviteCard inviteCard = new InviteCard(client,invite,groupsList);
                            loader.setController(inviteCard);
                            Node node = loader.load();
                            invitesList.getChildren().add(node);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Breaking notification thread.");
            e.printStackTrace();
            return;
        }
    }
}
