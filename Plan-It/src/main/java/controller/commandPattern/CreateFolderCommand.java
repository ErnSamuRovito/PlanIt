package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.folder.FolderDB;
import model.dao.user.UserDAOImpl;
import view.panel.panelDecorators.FolderCreateDecorator;

import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class CreateFolderCommand implements ActionCommand{
    private final FolderCreateDecorator createFolderDecorator;
    FolderDB newFolderDB;

    public CreateFolderCommand(FolderCreateDecorator createFolderDecorator) {
        this.createFolderDecorator = createFolderDecorator;
    }

    @Override
    public void execute() {
        if (!createFolderDecorator.getTextFieldName().isEmpty() && !createFolderDecorator.getTextFieldName().matches(".*[/*.,?^].*")) {
            try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
                UserDAOImpl userDAO = new UserDAOImpl(connection);
                int userId = userDAO.getUserByUsername(ComponentManager.getInstance().getUser()).getId();
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                int parentId = folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolder(), ComponentManager.getInstance().getUser()
                );

                newFolderDB = new FolderDB(
                        createFolderDecorator.getTextFieldName(),
                        userId,
                        parentId
                );

                FolderDAOImpl folderDAOimpl = new FolderDAOImpl(connection);
                folderDAOimpl.addFolder(newFolderDB);
                System.out.println("Folder created : " + newFolderDB.getFolderName());
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            } catch (SQLException e) {throw new RuntimeException(e);}
        }else{
            showMessageDialog(null, "A name for the folder is required and cannot include the following characters: '/*.,?^'.", "Plan-It", ERROR_MESSAGE);
        }
    }
}
