package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTaskDataLoader {
    private final String taskTitle, startFolder;
    public DatabaseTaskDataLoader(String taskTitle, String startFolder){
        this.taskTitle=taskTitle;
        this.startFolder=startFolder;
    }

    public List<String> getTaskData(){
        List<String> data = new ArrayList<>();
        String query = """
            SELECT title, description, due_date, urgency, folder, state, type, extra_info
            FROM Task
            WHERE title = ? and folder = (SELECT id FROM Folder WHERE folder_name=?);
        """;
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, taskTitle);
            statement.setString(2, startFolder);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    data.add(resultSet.getString("title"));
                    data.add(resultSet.getString("description"));
                    data.add(resultSet.getString("due_date"));
                    data.add(resultSet.getString("urgency"));
                    data.add(resultSet.getString("folder"));
                    data.add(resultSet.getString("state"));
                    data.add(resultSet.getString("type"));
                    data.add(resultSet.getString("extra_info"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching task data from database", e);
        }

        return data;
    }
}
