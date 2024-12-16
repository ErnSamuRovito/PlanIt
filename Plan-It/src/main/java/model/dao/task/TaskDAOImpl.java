package model.dao.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private Connection connection;

    public TaskDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addTask(TaskDB task) {
        String sql = "INSERT INTO Task (title,description,due_date,urgency,folder,state,type,extra_info) VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDueDate());
            stmt.setInt(4, task.getUrgency());
            stmt.setInt(5, task.getFolder());
            stmt.setInt(6, task.getState());
            stmt.setInt(7, task.getType());
            stmt.setString(8, task.getExtraInfo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TaskDB getTaskById(int id) {
        String sql = "SELECT * FROM Task WHERE id_task = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaskDB(
                        rs.getInt("id_task"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getInt("urgency"),
                        rs.getInt("folder"),
                        rs.getInt("state"),
                        rs.getInt("type"),
                        rs.getString("extra_info")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TaskDB> getTasksByFolder(int folderId) {
        List<TaskDB> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE folder = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, folderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tasks.add(
                    new TaskDB(
                        rs.getInt("id_task"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("due_date"),
                        rs.getInt("urgency"),
                        rs.getInt("folder"),
                        rs.getInt("state"),
                        rs.getInt("type"),
                        rs.getString("extra_info")
                    )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void updateTask(TaskDB task) {
        String sql = "UPDATE Task SET title=?,description=?,due_date=?,urgency=?,folder=?,state=?,type=?,extra_info=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setString(3, task.getDueDate());
            stmt.setInt(4, task.getUrgency());
            stmt.setInt(5, task.getFolder());
            stmt.setInt(6, task.getState());
            stmt.setInt(7, task.getType());
            stmt.setString(8, task.getExtraInfo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM Task WHERE id_task = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getTaskDataByTitleAndFolderAndUsername(String taskTitle, String folderName, String username) {
        ArrayList<String> data = new ArrayList<>();
        String query = """
            SELECT title, description, due_date, urgency, folder, state, type, extra_info
            FROM Task
            WHERE title = ? AND folder = (SELECT id FROM Folder WHERE folder_name = ? AND owner = (SELECT id FROM User WHERE username = ?));
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, taskTitle);
            statement.setString(2, folderName);
            statement.setString(3, username);

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

    @Override
    public List<String> getTasksByFolderAndUser(String startFolder, String user) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (startFolder.equals("/")) {
                statement.setNull(1, java.sql.Types.VARCHAR); // folder name
                statement.setNull(4, java.sql.Types.VARCHAR); // folder name
            } else {
                statement.setString(1, startFolder); // folder name
                statement.setString(4, startFolder); // folder name
            }
            statement.setString(2, user); // username or email
            statement.setString(3, user); // username or email

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
