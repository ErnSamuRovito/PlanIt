package model.dao.task;

import java.util.ArrayList;
import java.util.List;

public interface TaskDAO {
    void addTask(Task task);
    Task getTaskById(int id);
    List<Task> getTasksByFolder(int folderId);
    void updateTask(Task task);
    void deleteTask(int id);

    ArrayList<String> getTaskDataByTitleAndFolder(String taskTitle, String folderName);
    List<String> getTasksByFolderAndUser(String startFolder, String user);
}
