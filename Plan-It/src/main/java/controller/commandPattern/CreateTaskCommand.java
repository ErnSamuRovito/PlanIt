package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.composite.Task;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import view.panel.panelDecorators.TaskCreateDecorator;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class CreateTaskCommand implements ActionCommand {
    private final TaskCreateDecorator taskCreateDecorator;

    public CreateTaskCommand(TaskCreateDecorator taskCreateDecorator) {
        this.taskCreateDecorator = taskCreateDecorator;
    }

    @Override
    public void execute() {
        String nameTask = taskCreateDecorator.getNameTaskField();
        String descriptionTask = taskCreateDecorator.getDescriptionTaskPane();
        String dateTask = taskCreateDecorator.getCustomDataPicker();
        int urgencyTask = taskCreateDecorator.getComboBoxSelection();
        int typeTask = taskCreateDecorator.getComboBoxSelection();

        if (!nameTask.isEmpty() && !nameTask.matches(".*[/*.,?^].*")) {
            try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                int folderId = folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolder(),
                        ComponentManager.getInstance().getUser()
                );

                Task newTask = new Task(
                        nameTask,
                        descriptionTask,
                        dateTask,
                        urgencyTask,
                        folderId,
                        typeTask,
                        "task extra info"
                );

                TaskDAOImpl taskDAO = new TaskDAOImpl(connection);

                boolean success = taskDAO.addTask(newTask);

                if (!success) {
                    JOptionPane.showMessageDialog(taskCreateDecorator,
                            "Error: A task with the same name already exists.",
                            "Creation Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            showMessageDialog(null, "A name for the task is required and cannot include special characters.", "Plan-It", ERROR_MESSAGE);
        }
    }
}
