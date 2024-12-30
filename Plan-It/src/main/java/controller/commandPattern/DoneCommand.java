package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.task.TaskDAOImpl;
import model.plant.AvatarPlant;

import java.sql.Connection;
import java.sql.SQLException;

public class DoneCommand implements ActionCommand{
    int id_task_completed;

    public DoneCommand(String id_task) {
        id_task_completed = Integer.parseInt(id_task);
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            if (taskDAO.markTaskAsDone(id_task_completed))
            {
                System.out.println("Task done!");
            }
            AvatarPlant.getInstance().addHP(10);
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
