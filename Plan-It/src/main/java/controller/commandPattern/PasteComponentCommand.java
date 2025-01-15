package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;

import javax.swing.*;
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
                        ComponentManager.getInstance().getCurrFolderName(),
                        ComponentManager.getInstance().getUser()
                );
                if (!folderDAO.checkFolderExistsInParent(newParentId,ComponentManager.getInstance().getCuttedComponentId())) {
                    folderDAO.updateFolderParent(
                            ComponentManager.getInstance().getCuttedComponentId(),
                            newParentId
                    );
                }else{
                    JOptionPane.showMessageDialog(null,
                        "Error: A folder with the same name already exists in the destination folder.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }else if (ComponentManager.getInstance().getCuttedComponentType().equals("task")){
                newParentId=folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolderName(),
                        ComponentManager.getInstance().getUser()
                );
                if (!taskDAO.checkTaskExistsInFolder(newParentId,ComponentManager.getInstance().getCuttedComponentId())) {
                    taskDAO.updateTaskFolder(
                            ComponentManager.getInstance().getCuttedComponentId(),
                            newParentId
                    );
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Error: A task with the same name already exists in the destination folder.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            //resetto il componente "tagliato".
            ComponentManager.getInstance().setCuttedComponent(null,null);
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView()); //Aggiorno la DeskView
        }catch (SQLException e) {throw new RuntimeException(e);}
    }
}
