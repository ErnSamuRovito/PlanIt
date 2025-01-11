package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.composite.Folder;
import model.dao.folder.FolderDAOImpl;
import model.dao.user.UserDAOImpl;
import view.panel.panelDecorators.FolderCreateDecorator;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class CreateFolderCommand implements ActionCommand {
    private final FolderCreateDecorator createFolderDecorator;

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

                // Creiamo un oggetto Folder
                Folder newFolder = new Folder(createFolderDecorator.getTextFieldName(), userId, parentId);

                // Aggiungi la nuova cartella nel database
                FolderDAOImpl folderDAOImpl = new FolderDAOImpl(connection);
                boolean success = folderDAOImpl.addFolder(newFolder);

                if (!success) {
                    // Se la cartella non è stata creata (cartella con lo stesso nome esistente)
                    JOptionPane.showMessageDialog(createFolderDecorator,
                            "Error: A folder with the same name already exists.",
                            "Creation error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Ricarica la vista della scrivania
                    ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Database error while creating folder", e);
            }
        } else {
            // Messaggio di errore se il nome non è valido
            showMessageDialog(null, "A name for the folder is required and cannot include the following characters: '/*.,?^'.", "Plan-It", ERROR_MESSAGE);
        }
    }
}
