package BackEnd.BackEnd.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    public static Connection db() throws SQLException {
        String url = "jdbc:mysql://localhost:3303/crud";
        String username = "root";
        String password = "pass4142";
        return DriverManager.getConnection(url, username, password);
    }
}
//jiujhuhuihuih
