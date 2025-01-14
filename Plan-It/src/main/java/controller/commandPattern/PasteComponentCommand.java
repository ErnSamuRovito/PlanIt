package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class PasteComponentCommand implements ActionCommand {
    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            int newParentId=-1;
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);

            if (ComponentManager.getInstance().getCuttedComponentType().equals("folder")) {
                //cartella in cui devo andare
                newParentId=folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolder(),
                        ComponentManager.getInstance().getUser()
                );
                folderDAO.updateFolderParent(
                        ComponentManager.getInstance().getCuttedComponentId(),
                        newParentId
                );
            }else if (ComponentManager.getInstance().getCuttedComponentType().equals("task")){
                newParentId=folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolder(),
                        ComponentManager.getInstance().getUser()
                );
                taskDAO.updateTaskFolder(
                        ComponentManager.getInstance().getCuttedComponentId(),
                        newParentId
                );
            }

            System.out.println("Sposto il "+ComponentManager.getInstance().getCuttedComponentType()+" id: "+ComponentManager.getInstance().getCuttedComponentId()+" in "+newParentId);
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView()); //Aggiorno la DeskView
        }catch (SQLException e) {throw new RuntimeException(e);}
    }
}
