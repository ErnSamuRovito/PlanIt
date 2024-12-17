package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.User;
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

        newTaskDB = new TaskDB(nameTask,
                descriptionTask,
                dateTask,
                urgencyTask,
                1, // test
                1,   // test
                1,   // test
                "task extra info");  // test

        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            TaskDAOImpl taskDAOimpl = new TaskDAOImpl(connection);
            taskDAOimpl.addTask(newTaskDB);
            System.out.println("task created : " + nameTask);
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView(User.getInstance().getUsername(),"/root"));
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
