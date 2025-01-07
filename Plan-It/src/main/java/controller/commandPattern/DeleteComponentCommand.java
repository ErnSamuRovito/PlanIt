package controller.commandPattern;

import core.ComponentManager;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteComponentCommand implements ActionCommand {
    private final String title, type;
    private final int id;

    // Costruttore per passare l'ID della folder da eliminare
    public DeleteComponentCommand(String title, String imagePath) {
        this.title = title;

        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            if (imagePath.equals(GlobalResources.folderImage)){
                type = "folder";
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                id = folderDAO.getFolderIdByNameAndOwner(title, ComponentManager.getInstance().getUser());
            } else {
                type = "task";
                TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
                id = taskDAO.getIdByFolderNameAndOwnerAndTitle(
                        ComponentManager.getInstance().getCurrFolder(),
                        ComponentManager.getInstance().getUser(),
                        title
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute() {
        // Mostra la finestra di conferma prima di procedere con la cancellazione
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete the " + (type.equals("folder") ? "folder" : "task") + " '" + title + "'?", "Delete Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
                if (type.equals("folder")) {
                    FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                    folderDAO.deleteFolder(id);
                } else if (type.equals("task")) {
                    TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
                    taskDAO.deleteTask(id);
                }
            } catch (Exception e) {
                System.err.println("Errore durante l'eliminazione della " + (type.equals("folder") ? "folder" : "task") + " con ID " + id);
                e.printStackTrace();
            }
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        }
    }
}
