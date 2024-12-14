package core;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFileLoader {
    private String user, startFolder="/";
    public void setStartFolder(String startFolder) {this.startFolder = startFolder;}
    public void setUser(String user) {this.user = user;}
    public String getStartFolder() {return startFolder;}
    public String getUser() {return user;}

    public DatabaseFileLoader(String user) {
        this.user = user;
    }

    public List<String> getFolders() {
        List<String> folders = new ArrayList<>();
        String query = """
            SELECT folder_name
            FROM Folder
            WHERE (parent = (
                    SELECT id
                    FROM Folder
                    WHERE folder_name = ?
                    AND owner IN (
                        SELECT id
                        FROM User
                        WHERE username = ? OR email = ?
                    )
                ) OR (parent IS NULL AND ? IS NULL))
            AND owner IN (
                SELECT id
                FROM User
                WHERE username = ? OR email = ?
            );
        """;
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (startFolder.equals("/")) {
                //per cercare cartelle root
                statement.setNull(1, java.sql.Types.VARCHAR); //folder name
                statement.setNull(4, java.sql.Types.VARCHAR); //folder name
            }else{
                //per cercare sottocartelle di una cartella specifica
                statement.setString(1, startFolder); //folder name
                statement.setString(4, startFolder); //folder name
            }
            statement.setString(2, user); //nome o email
            statement.setString(3, user); //nome o email
            statement.setString(5, user); //nome o email
            statement.setString(6, user); //nome o email
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

    public List<String> getTasks() {
        List<String> tasks = new ArrayList<>();
        String query = """
            SELECT title
            FROM Task
            WHERE (folder = (
                    SELECT id
                    FROM Folder
                    WHERE folder_name = ?
                    AND owner IN (
                        SELECT id
                        FROM User
                        WHERE username = ? OR email = ?
                    )
            ) OR (folder IS NULL AND ? IS NULL));
        """;
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if (startFolder.equals("/")) {
                //per cercare cartelle root
                statement.setNull(1, java.sql.Types.VARCHAR); //folder name
                statement.setNull(4, java.sql.Types.VARCHAR); //folder name
            }else{
                //per cercare sottocartelle di una cartella specifica
                statement.setString(1, startFolder); //folder name
                statement.setString(4, startFolder); //folder name
            }
            statement.setString(2, user); //nome o email
            statement.setString(3, user); //nome o email

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
}
