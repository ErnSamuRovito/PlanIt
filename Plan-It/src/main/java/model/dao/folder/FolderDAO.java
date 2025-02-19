package model.dao.folder;

import java.util.List;

public interface FolderDAO {
    boolean addFolder(FolderDB folder);
    Boolean addRootFolder(int owner);
    FolderDB getFolderById(int id);
    int getFolderIdByNameAndOwner(String folderName,String owner);
    List<FolderDB> getFoldersByOwner(int ownerId);
    List<FolderDB> getSubfolders(int parentId);
    boolean updateFolder(FolderDB folder, int id);
    void deleteFolder(int id);
    String findParentFolder(String name);

    List<String> getFoldersByFolderAndUser(String startFolder, String user);
}
