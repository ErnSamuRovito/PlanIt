package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import model.dao.task.TaskDB;
import view.panel.panelDecorators.TaskCreateDecorator;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

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
        String descriptionTask = taskCreateDecorator.getDescriptionTaskPane();
        String dateTask = taskCreateDecorator.getCustomDataPicker();
        int urgencyTask = taskCreateDecorator.getComboBoxSelection();
        int typeTask = taskCreateDecorator.getComboBoxSelection();

        if (!nameTask.isEmpty() && !nameTask.matches(".*[/*.,?^].*")) {
            try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
                FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
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

                boolean success=taskDAOimpl.addTask(newTaskDB);

                if (!success) {
                    // Se l'aggiornamento non è riuscito, mostra un pop-up di errore
                    JOptionPane.showMessageDialog(taskCreateDecorator,
                            "Error: A task with the same name already exists.",
                            "Creation Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Ricarica la vista della scrivania
                    ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
                }

                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            showMessageDialog(null, "A name for the task is required and cannot include the following characters: '/*.,?^'.", "Plan-It", ERROR_MESSAGE);
        }
    }
}
