package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.composite.Folder;
import model.dao.folder.FolderDAOImpl;
import view.panel.panelDecorators.FolderModifyDecorator;

import javax.swing.*;
import java.sql.Connection;

public class ModifyFolderCommand implements ActionCommand {
    private final FolderModifyDecorator panel;

    public ModifyFolderCommand(FolderModifyDecorator panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);

            // Ottieni l'ID della cartella a partire dal nome e dall'utente
            int folderId = panel.getFolderId();
            if (folderId == -1) {
                return; // Se non è stata trovata la cartella, esci dal metodo
            }

            // Recupera la cartella dal DB per aggiornarla
            Folder folderToUpdate = folderDAO.getFolderById(folderId);
            if (folderToUpdate == null) {
                return; // Se la cartella non esiste, esci dal metodo
            }

            // Imposta il nuovo nome della cartella
            folderToUpdate.setFolderName(panel.getTextFieldName());

            // Esegui l'aggiornamento nel DB
            boolean success = folderDAO.updateFolder(folderToUpdate, folderId);

            if (!success) {
                // Se l'aggiornamento non è riuscito, mostra un pop-up di errore
                JOptionPane.showMessageDialog(panel,
                        "Error: A folder with the same name already exists.",
                        "Update Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ricarica la vista della scrivania
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel,
                    "An error occurred while updating the folder.",
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
