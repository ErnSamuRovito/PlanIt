package controller.commandPattern;

import core.ComponentManager;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class CutComponentCommand implements ActionCommand{
    private String title, imagePath;
    public CutComponentCommand(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            if (imagePath.equals(GlobalResources.folderImage)){
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                int folderId = folderDAO.getFolderIdByNameAndOwner(
                        title,
                        ComponentManager.getInstance().getUser()
                );
                ComponentManager.getInstance().setCuttedComponent("folder",folderId);
            }else{
                TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
                int taskId = taskDAO.getIdByFolderNameAndOwnerAndTitle(
                        ComponentManager.getInstance().getCurrFolderName(),
                        ComponentManager.getInstance().getUser(),
                        title
                );
                ComponentManager.getInstance().setCuttedComponent("task",taskId);
            }

            System.out.println("Cutted Component: "+ComponentManager.getInstance().getCuttedComponentType()+" - "+ComponentManager.getInstance().getCuttedComponentId());
        }catch (SQLException e) {throw new RuntimeException(e);}
    }
}
