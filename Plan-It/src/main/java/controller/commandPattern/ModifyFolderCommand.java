package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.folder.FolderDB;
import view.panel.panelDecorators.FolderModifyDecorator;

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
                System.out.println("Folder not found!");
                return;
            }

            // Recupera la cartella dal DB per aggiornarla
            FolderDB updatedFolder = folderDAO.getFolderById(folderId);
            if (updatedFolder == null) {
                System.out.println("Folder not retrieved from DB.");
                return;
            }

            // Imposta il nuovo nome della cartella
            System.out.println("NUOVO NOME CARTELLA: "+panel.getTextFieldName());
            updatedFolder.setFolderName(panel.getTextFieldName());

            // Esegui l'aggiornamento nel DB
            folderDAO.updateFolder(updatedFolder,folderId);

            // Ricarica la vista della scrivania
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
