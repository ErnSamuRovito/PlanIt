package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.composite.Task;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import view.panels.TaskCreateView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class CreateTaskCommand implements ActionCommand {
    private final TaskCreateView taskCreateView;

    public CreateTaskCommand(TaskCreateView taskCreateView) {
        this.taskCreateView = taskCreateView;
    }

    @Override
    public void execute() {
        String titleTask = taskCreateView.getTitle();
        String descriptionTask = taskCreateView.getDescription();
        String dateTask = taskCreateView.getDate();
        int urgencyTask = taskCreateView.getUrgency();

        if (!titleTask.isEmpty() && !titleTask.matches(".*[/*.,?^].*")) {
            try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
                int folderId = folderDAO.getFolderIdByNameAndOwner(
                        ComponentManager.getInstance().getCurrFolderName(),
                        ComponentManager.getInstance().getUser()
                );

                Task newTask = new Task(
                        titleTask,
                        descriptionTask,
                        dateTask,
                        urgencyTask,
                        folderId,
                        1,
                        "task extra info"
                );

                TaskDAOImpl taskDAO = new TaskDAOImpl(connection);

                boolean success = taskDAO.addTask(newTask);

                if (!success) {
                    JOptionPane.showMessageDialog(taskCreateView,
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
