package model.dao.folder;

import java.util.List;

public interface FolderDAO {
    void addFolder(Folder folder);
    Folder getFolderById(int id);
    List<Folder> getFoldersByOwner(int ownerId);
    List<Folder> getSubfolders(int parentId);
    void updateFolder(Folder folder);
    void deleteFolder(int id);

    List<String> getFoldersByFolderAndUser(String startFolder, String user);
}
