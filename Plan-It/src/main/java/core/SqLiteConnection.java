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
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.", e);
        }
        return connection;
    }
}
