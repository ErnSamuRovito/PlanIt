package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import controller.controllers.FolderController;
import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.services.FolderService;

import java.sql.Connection;
import java.sql.SQLException;

public class GoBackCommand implements ActionCommand {
    @Override public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            String user=ComponentManager.getInstance().getUser();
            String currFolder=ComponentManager.getInstance().getCurrFolderName();

            int folderId=folderDAO.getFolderIdByNameAndOwner(currFolder,ComponentManager.getInstance().getUser());
            int parentId=folderDAO.findParentFolder(folderId);

            ComponentManager.getInstance().setPath(user, parentId);
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
