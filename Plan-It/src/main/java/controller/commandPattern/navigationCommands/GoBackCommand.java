package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import java.sql.Connection;
import java.sql.SQLException;

public class GoBackCommand implements ActionCommand {
    @Override public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            String user=ComponentManager.getInstance().getUser();
            String currFolder=ComponentManager.getInstance().getCurrFolder();
            ComponentManager.getInstance().setPath(user, folderDAO.findParentFolder(currFolder));
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
