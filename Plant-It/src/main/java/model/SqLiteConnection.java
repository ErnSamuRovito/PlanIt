package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Paths;

public class SqLiteConnection {
    private Connection connection;

    public SqLiteConnection() {
        // Verifica se il driver è disponibile nel classpath
        try {
            // Controlla se il driver è disponibile
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found in classpath.");
            e.printStackTrace();  // Stampa l'eccezione per maggiore visibilità
            throw new RuntimeException(e);
        }

        // Usa il percorso relativo al database
        String url = "jdbc:sqlite:src/main/java/resources/planIt.db";

        try {
            // Prova a connetterti al database
            connection = DriverManager.getConnection(url);
            System.out.println("Connected to database successfully: " + url);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + url);
            e.printStackTrace();  // Aggiungi per maggiore visibilità dell'errore
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                System.out.println("Closing connection successfully: " + connection);
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + connection);
            throw new RuntimeException(e);
        }
    }
}
