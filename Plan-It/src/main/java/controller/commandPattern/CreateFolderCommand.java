package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.User;
import model.dao.folder.FolderDAOImpl;
import model.dao.folder.FolderDB;
import view.panel.createPanel.FolderCreateDecorator;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateFolderCommand implements ActionCommand{
    private final FolderCreateDecorator createFolderDecorator;
    FolderDB newFolderDB;

    public CreateFolderCommand(FolderCreateDecorator createFolderDecorator) {
        this.createFolderDecorator = createFolderDecorator;
    }

    @Override
    public void execute() {
        newFolderDB = new FolderDB(createFolderDecorator.getTextFieldName(),
                User.getInstance().getId(),
                1); // test

        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            FolderDAOImpl folderDAOimpl = new FolderDAOImpl(connection);
            folderDAOimpl.addFolder(newFolderDB);
            System.out.println("Folder created : " + newFolderDB.getFolderName());
            ComponentManager.getInstance().setPanel(
                    ComponentManager.getInstance().getDeskView(User.getInstance().getUsername(),"/root"));
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
