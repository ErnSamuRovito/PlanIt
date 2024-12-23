package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import model.dao.task.TaskDB;
import view.panel.createPanel.TaskCreateDecorator;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateTaskCommand implements ActionCommand{
    private final TaskCreateDecorator taskCreateDecorator;
    TaskDB newTaskDB;
    String nameTask;

    public CreateTaskCommand(TaskCreateDecorator taskCreateDecorator) {
        this.taskCreateDecorator = taskCreateDecorator;
    }

    @Override
    public void execute() {
        nameTask = taskCreateDecorator.getNameTaskField();
        String descriptionTask = taskCreateDecorator.getDescriptionTaskField();
        String dateTask = taskCreateDecorator.getCustomDataPicker();
        int urgencyTask = taskCreateDecorator.getComboBoxSelection();
        int typeTask = taskCreateDecorator.getComboBoxSelection();

        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            FolderDAOImpl folderDAO=new FolderDAOImpl(connection);
            newTaskDB = new TaskDB(
                    nameTask,
                    descriptionTask,
                    dateTask,
                    urgencyTask,
                    folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolder(),
                        ComponentManager.getInstance().getUser()
                    ),
                    0,
                    typeTask,
                    "task extra info"
            );

            TaskDAOImpl taskDAOimpl = new TaskDAOImpl(connection);
            taskDAOimpl.addTask(newTaskDB);
            System.out.println("task created : " + nameTask);
            /*ComponentManager.getInstance().setPath(
                    ComponentManager.getInstance().getUser(),ComponentManager.getInstance().getCurrFolder()
            );*/
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
