package core;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDataProvider implements DataProvider {
    private String user;
    private Connection connection;

    public DatabaseDataProvider(String user) {
        this.user = user;
    }

    // Apre la connessione se non è già aperta
    private Connection getValidConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // La connessione viene aperta una sola volta
            connection = SqLiteConnection.getInstance().getConnection();
            System.out.println("Connessione aperta.");
        } else {
            System.out.println("Connessione già aperta.");
        }
        return connection;
    }

    // Metodo per chiudere la connessione manualmente
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connessione chiusa manualmente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getFolders() {
        List<String> folders = new ArrayList<>();
        String query = "SELECT folder_name FROM Folder WHERE owner IN (SELECT id FROM User WHERE username=? or email=?)";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user);
            statement.setString(2, user);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    folders.add(resultSet.getString("folder_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching folders from database", e);
        }
        return folders;
    }

    @Override
    public List<String> getTasks() {
        List<String> tasks = new ArrayList<>();
        String query = "SELECT title FROM Task";
        try (PreparedStatement statement = getValidConnection().prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    tasks.add(resultSet.getString("title"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching tasks from database", e);
        }
        return tasks;
    }

    @Override
    public List<String> getTasksByFolder(String folderName) {
        return null;//List.of();
    }
}
