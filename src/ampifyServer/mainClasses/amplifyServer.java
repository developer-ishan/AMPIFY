package ampifyServer.mainClasses;

import ampifyServer.runnable.HandleServer;

import java.sql.Connection;
import java.sql.SQLException;

public class amplifyServer {

    public static void main(String[] args) {
        while (true)
        {
            try {
                Connection con = dbConnection.connect();
                Thread thread=new Thread(new HandleServer(con));
                thread.start();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Cannot Connect to DB");
            }
        }
    }
}
