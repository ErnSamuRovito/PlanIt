package model.dao.task;

import model.composite.Task;
import java.util.ArrayList;
import java.util.List;

public interface TaskDAO {
    boolean addTask(Task task);
    Task getTaskById(int id);
    List<Task> getTasksByFolder(int folderId);
    boolean updateTask(Task task, int id);
    void deleteTask(int id);

    ArrayList<String> getTaskDataByTitleAndFolderAndUsername(String taskTitle, String folderName, String username);
    List<String> getTasksByFolderAndUser(String startFolder, String user);
    boolean markTaskAsDone(int id_task);
    void markTaskAsExpired(int id_task);

    int checkTaskByFolderAndTitle(String folder, String user, String title);

    int getIdByFolderNameAndOwnerAndTitle(String folderName, String owner, String title);
}
