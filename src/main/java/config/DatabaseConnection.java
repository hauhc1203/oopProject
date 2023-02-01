package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hauhc1203
 */
public class DatabaseConnection {
    public static Connection getConnect(){
        String jdbcURL = "jdbc:mysql://localhost:3306/oopBigProject?useSSL=false";
        String jdbcUsername = "root";
        String jdbcPassword = "abcd1234";
        Connection connection ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }


}
