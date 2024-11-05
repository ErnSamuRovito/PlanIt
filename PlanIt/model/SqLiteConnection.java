package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqLiteConnection {
    private Connection connection;
    public SqLiteConnection() {
        String url = "jdbc:sqlite:planIt.db";

        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database successfully : " + url);
        } catch (SQLException e) {
            System.out.println("Error to database successfully : " + url);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                System.out.println("Closing connection successfully : " + connection);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error to database successfully : " + connection);
            throw new RuntimeException(e);
        }
    }
}