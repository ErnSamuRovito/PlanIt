package controller.commandPattern.componentCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import model.persistance.SqLiteConnection;
import model.persistance.dao.task.TaskDAOImpl;
import model.plantStates.AvatarPlant;

import java.sql.Connection;
import java.sql.SQLException;

public class TaskDoneCommand implements ActionCommand {
    int id_task_completed;

    public TaskDoneCommand(String id_task) {
        id_task_completed = Integer.parseInt(id_task);
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            taskDAO.markTaskAsDone(id_task_completed);
            AvatarPlant.getInstance().addHP(10);
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
