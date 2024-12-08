package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Paths;

public class SqLiteConnection {
    private static SqLiteConnection instance; // Unica istanza della classe
    private Connection connection;
    //private final String relativePath = "jdbc:sqlite:PlanIt.sqlite";
    private final String url = "jdbc:sqlite:PlanIt.sqlite";

    // Costruttore privato per impedire la creazione di nuove istanze
    private SqLiteConnection() {
        try {
            // Verifica se il driver è disponibile nel classpath
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found in classpath.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        try {
            // Connessione al database
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database successfully: " + url);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + url);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Metodo per ottenere l'unica istanza della classe
    public static SqLiteConnection getInstance() {
        if (instance == null) {
            synchronized (SqLiteConnection.class) {
                if (instance == null) {
                    instance = new SqLiteConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Reopening database connection...");
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.out.println("Error reopening database connection: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                System.out.println("Closing connection successfully: " + connection);
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + connection);
            throw new RuntimeException(e);
        }
    }
}
