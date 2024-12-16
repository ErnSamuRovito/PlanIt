package model.dao.folder;

import java.util.List;

public interface FolderDAO {
    void addFolder(FolderDB folder);
    FolderDB getFolderById(int id);
    List<FolderDB> getFoldersByOwner(int ownerId);
    List<FolderDB> getSubfolders(int parentId);
    void updateFolder(FolderDB folder);
    void deleteFolder(int id);

    List<String> getFoldersByFolderAndUser(String startFolder, String user);
}
