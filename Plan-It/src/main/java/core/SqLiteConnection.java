package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqLiteConnection {
    private static SqLiteConnection instance; // Unica istanza della classe
    private Connection connection;
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
    }

    // Metodo per ottenere l'unica istanza della classe
    public static synchronized SqLiteConnection getInstance() {
        if (instance == null) {
            synchronized (SqLiteConnection.class) {
                if (instance == null) {
                    instance = new SqLiteConnection();
                }
            }
        }
        return instance;
    }

    // Restituisce la connessione al database
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Opening new database connection...");
                connection = DriverManager.getConnection(url);
                System.out.println("Database connection opened successfully.");
            } else {
                System.out.println("Using existing database connection...");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.", e);
        }
        return connection;
    }


    // Chiude la connessione al database
    /*public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Closing database connection...");
                connection.close();
                connection = null; // Rende la connessione nulla dopo la chiusura
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
            e.printStackTrace(); // Aggiunta di un log dettagliato
        }
    }*/
}
