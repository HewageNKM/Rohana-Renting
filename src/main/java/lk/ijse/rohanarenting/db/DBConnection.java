/**
 * Created by Nadun Kawishika
 * date :
 * time :
 * project name :IntelliJ IDEA
 */
package lk.ijse.rohanarenting.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private final Connection connection;
    private DBConnection() throws SQLException {
    connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/rcompany","root","1234");
    }
    public static DBConnection getInstance() throws SQLException {
        return (dbConnection==null)?(dbConnection=new DBConnection()):(dbConnection);
    }
    public Connection getConnection(){
        return connection;
    }
}
