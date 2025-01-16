package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import model.persistance.SqLiteConnection;
import model.persistance.dao.folder.FolderDAOImpl;

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
