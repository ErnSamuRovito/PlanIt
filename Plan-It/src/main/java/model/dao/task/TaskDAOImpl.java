package model.dao.task;

import model.composite.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private Connection connection;

    public TaskDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addTask(Task task) {
        // Verifica se esiste già un task con lo stesso nome nella stessa cartella
        String checkSql = "SELECT COUNT(*) FROM Task WHERE title = ? AND folder = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, task.getTitle());
            checkStmt.setInt(2, task.getFolder());

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Se esiste già un task con lo stesso nome nella stessa cartella, l'inserimento fallisce
                    return false;
                }
            }

            // Se non esiste, esegui l'inserimento
            String sql = "INSERT INTO Task (title, description, due_date, urgency, folder, state, type, extra_info) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, task.getTitle());
                stmt.setString(2, task.getDescription());
                stmt.setString(3, task.getDueDate());
                stmt.setInt(4, task.getUrgency());
                stmt.setInt(5, task.getFolder());
                stmt.setInt(6, task.getState());
                stmt.setInt(7, task.getType());
                stmt.setString(8, task.getExtraInfo());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Se sono stati inseriti dei record, restituisce true
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Se si verifica un errore, restituisce false
        }
    }


    @Override
    public Task getTaskById(int id) {
        String sql = "SELECT * FROM Task WHERE id_task = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Task(
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
    public List<Task> getTasksByFolder(int folderId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM Task WHERE folder = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, folderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tasks.add(
                    new Task(
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
    public boolean updateTask(Task task, int id) {
        // Verifica se esiste già un task con lo stesso nome nella stessa cartella, ma con ID diverso
        String checkSql = "SELECT COUNT(*) FROM Task WHERE title = ? AND folder = ? AND id_task != ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setString(1, task.getTitle());
            checkStmt.setInt(2, task.getFolder());
            checkStmt.setInt(3, id); // Escludi il task corrente

            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Se esiste già un task con lo stesso nome nella stessa cartella, l'aggiornamento fallisce
                    return false;
                }
            }

            // Se non esiste, esegui l'aggiornamento
            String sql = """
                UPDATE Task
                SET title = ?, description = ?, due_date = ?, urgency = ?, folder = ?, state = ?, type = ?, extra_info = ?
                WHERE id_task = ?
                """;
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, task.getTitle());
                stmt.setString(2, task.getDescription());
                stmt.setString(3, task.getDueDate());
                stmt.setInt(4, task.getUrgency());
                stmt.setInt(5, task.getFolder());
                stmt.setInt(6, task.getState());
                stmt.setInt(7, task.getType());
                stmt.setString(8, task.getExtraInfo());
                stmt.setInt(9, id);  // ID del task
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0; // Se sono stati aggiornati dei record, restituisce true
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Se si verifica un errore, restituisce false
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
            SELECT id_task, title, description, due_date, urgency, folder, state, type, extra_info
            FROM Task
            WHERE title = ? AND folder = (SELECT id FROM Folder WHERE folder_name = ? AND owner = (SELECT id FROM User WHERE username = ?));
        """;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, taskTitle);
            statement.setString(2, folderName);
            statement.setString(3, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    data.add(resultSet.getString("id_task"));
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
                statement.setNull(1, Types.VARCHAR); // folder name
                statement.setNull(4, Types.VARCHAR); // folder name
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

    @Override
    public boolean markTaskAsDone(int id_task) {
        String sql = "UPDATE Task SET state = 100 WHERE id_task = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_task);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void markTaskAsExpired(int id_task) {
        String sql = "UPDATE Task SET state = -1 WHERE id_task = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_task);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int checkTaskByFolderAndTitle(String folder, String user, String title){
        String sql="""
                        SELECT state
                        FROM Task
                        WHERE folder = (
                            SELECT id
                            FROM Folder
                            WHERE folder_name = ? AND owner = (
                                SELECT id
                                FROM User
                                WHERE username = ?
                            ) and title=?
                        );
                    """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, folder);
            stmt.setString(2, user);
            stmt.setString(3, title);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.getInt("state");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -10000;
        }
    }

    @Override
    public int getIdByFolderNameAndOwnerAndTitle(String folderName, String owner, String title) {
        String sql = """
        SELECT id_task
        FROM Task
        WHERE title = ? AND folder = (
            SELECT id
            FROM Folder
            WHERE folder_name = ? AND owner = (
                SELECT id
                FROM User
                WHERE username = ?
            )
        )
    """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, folderName);
            stmt.setString(3, owner);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) { // Controlla se ci sono risultati
                    return resultSet.getInt("id_task");
                } else {
                    return -1; // Valore di default se nessun risultato trovato
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -10000; // Valore di errore
        }
    }

    @Override
    public boolean updateTaskFolder(int taskId, int newFolderId) {
        String sql = "UPDATE Task SET folder = ? WHERE id_task = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, newFolderId);
            stmt.setInt(2, taskId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Task> getTasksDueTodayByUser(String username) {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = """
        SELECT * FROM Task
        WHERE due_date = strftime('%d/%m/%Y', 'now')
        AND folder IN (
            SELECT id FROM Folder WHERE owner = (SELECT id FROM User WHERE username = ?)
        )
    """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tasks.add(new Task(
                            rs.getInt("id_task"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getString("due_date"),
                            rs.getInt("urgency"),
                            rs.getInt("folder"),
                            rs.getInt("state"),
                            rs.getInt("type"),
                            rs.getString("extra_info")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public int getFolderIdByTaskId(int taskId) {
        int folderId = -1;
        String sql = """
        SELECT folder
        FROM Task
        WHERE id_task = ?;
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, taskId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    folderId = resultSet.getInt("folder"); // Ottieni l'id della cartella
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return folderId; // Restituisci il nome della cartella, o null se non trovato
    }

    @Override
    public boolean checkTaskExistsInFolder(int folderId, Integer taskId) {
        String sql = """
        SELECT COUNT(*)
        FROM Task
        WHERE folder = ? AND title=(
            SELECT title FROM Task WHERE id_task = ?
        )
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, folderId);
            stmt.setInt(2, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.getInt(1) > 0; // Restituisce true se esiste un task con lo stesso nome
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Se si verifica un errore, restituisce false
        }
    }
}
