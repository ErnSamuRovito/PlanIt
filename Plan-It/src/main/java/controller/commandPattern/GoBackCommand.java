package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import java.sql.Connection;
import java.sql.SQLException;

public class GoBackCommand implements ActionCommand{
    private final String user, startFolder;
    public GoBackCommand(String user, String startFolder) {
        this.user = user;
        this.startFolder = startFolder;
    }
    @Override public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            ComponentManager.getInstance().setPath(user, folderDAO.findParentFolder(startFolder));
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
