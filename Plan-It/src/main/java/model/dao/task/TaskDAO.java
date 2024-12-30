package model.dao.task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TaskDAO {
    void addTask(TaskDB task);
    TaskDB getTaskById(int id);
    List<TaskDB> getTasksByFolder(int folderId);
    void updateTask(TaskDB task);
    void deleteTask(int id);

    ArrayList<String> getTaskDataByTitleAndFolderAndUsername(String taskTitle, String folderName, String username);
    List<String> getTasksByFolderAndUser(String startFolder, String user);
    boolean markTaskAsDone(int id_task);
    boolean markTaskAsExpired(int id_task);
}
