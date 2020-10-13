package ampifyServer.server;

/*

  @author ishan
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbConnection {
    // JDBC driver name and database URL
    private static final String jdbcDriver = "org.mariadb.jdbc.Driver";
    private static  final String dbURL = "jdbc:mariadb://localhost:3306/ampify";

    // Database credentials
    private static final String user   = "root";
    private static final String pass  = "root";
//    private static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
//    private static  final String dbURL = "jdbc:mysql://localhost/softabliz?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    // Database credentials
//    private static final String user   = "developer-arun";
//    private static final String pass  = "developer-arun";
    public static Connection connect() throws SQLException{
        System.out.println("Connecting to DB....");
        // Register the jdbc driver
        try {
            Class.forName(jdbcDriver);
            System.out.println("Connected to DB.");
            return DriverManager.getConnection(dbURL,user,pass);
        }
        catch (ClassNotFoundException ex){
            System.err.println("Could not find jdbc driver.");
            Logger.getLogger(dbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
