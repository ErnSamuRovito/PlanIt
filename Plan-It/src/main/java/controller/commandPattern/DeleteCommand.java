package controller.commandPattern;

import core.ComponentManager;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import model.plant.AvatarPlant;

import java.sql.Connection;
import java.sql.SQLException;

public class DeleteCommand implements ActionCommand {
    private final String title,type;
    private final int id;

    // Costruttore per passare l'ID della folder da eliminare
    public DeleteCommand(String title, String imagePath) {
        this.title = title;

        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            if (imagePath.equals(GlobalResources.folderImage)){
                type="folder";
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                id=folderDAO.getFolderIdByNameAndOwner(title,ComponentManager.getInstance().getUser());
            } else{
                type="task";
                TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
                id=taskDAO.getIdByFolderNameAndOwnerAndTitle(
                        ComponentManager.getInstance().getCurrFolder(),
                        ComponentManager.getInstance().getUser(),
                        title
                );
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            if (type.equals("folder")) {
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                folderDAO.deleteFolder(id);
                System.out.println("Folder" + title + "con ID " + id + " eliminata con successo.");
            }else if (type.equals("task")){
                TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
                taskDAO.deleteTask(id);
                System.out.println("Task" + title + "con ID " + id + " eliminata con successo.");
            }
        } catch (Exception e) {
            System.err.println("Errore durante l'eliminazione della folder con ID " + id);
            e.printStackTrace();
        }
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
    }
}