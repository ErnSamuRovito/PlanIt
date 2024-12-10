package core;

import java.util.List;

public interface DataProvider {
    List<String> getFolders();
    List<String> getTasks();
    List<String> getTasksByFolder(String folderName);

    void setStartFolder(String startFolder);
    void setUser(String user);

    String getStartFolder();
    String getUser();
}