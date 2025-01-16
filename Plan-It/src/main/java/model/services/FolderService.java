package model.services;

import model.persistance.SqLiteConnection;
import model.composite.Folder;
import model.persistance.dao.folder.FolderDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class FolderService {
    public int getFolderIdByNameAndOwner(String folder, String user) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            return folderDAO.getFolderIdByNameAndOwner(folder,user);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Folder getFolderById(int id){
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            return folderDAO.getFolderById(id);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
