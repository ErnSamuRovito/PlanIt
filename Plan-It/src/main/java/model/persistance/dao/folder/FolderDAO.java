package model.persistance.dao.folder;

import model.composite.Folder;

import java.util.List;

public interface FolderDAO {
    boolean addFolder(Folder folder);
    Boolean addRootFolder(int owner);
    Folder getFolderById(int id);
    int getFolderIdByNameAndOwner(String folderName,String owner);
    List<Folder> getFoldersByOwner(int ownerId);
    List<Folder> getSubfolders(int parentId);
    boolean updateFolder(Folder folder, int id);
    void deleteFolder(int id);
    int findParentFolder(int folderId);

    List<String> getFoldersByFolderAndUser(String startFolder, String user);

    boolean updateFolderParent(int folderId, int newParentId);
    boolean checkFolderExistsInParent(int parentId, Integer folderId);
}
