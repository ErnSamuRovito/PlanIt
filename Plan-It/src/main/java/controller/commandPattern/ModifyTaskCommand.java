package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.task.TaskDAOImpl;
import model.dao.task.TaskDB;
import view.panel.panelDecorators.TaskModifyDecorator;

import javax.swing.*;
import java.sql.Connection;

public class ModifyTaskCommand implements ActionCommand {
    private final TaskModifyDecorator panel;

    public ModifyTaskCommand(TaskModifyDecorator panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);

            int id = taskDAO.getIdByFolderNameAndOwnerAndTitle(
                    ComponentManager.getInstance().getCurrFolder(),
                    ComponentManager.getInstance().getUser(),
                    panel.getTitle()
            );

            if (id == -1) {
                return;
            }

            // Cerca il task dal database usando l'id
            TaskDB updatedTask = taskDAO.getTaskById(id);
            if (updatedTask == null) {
                return;
            }

            // Applica gli aggiornamenti al task
            updatedTask.setTitle(panel.getNewTitle());
            updatedTask.setDescription(panel.getNewDescription());
            updatedTask.setDueDate(panel.getNewDueDate());
            updatedTask.setUrgency(panel.getNewUrgency());

            // Aggiorna il task nel database
            boolean success=taskDAO.updateTask(updatedTask, id);

            if (!success) {
                // Se l'aggiornamento non è riuscito, mostra un pop-up di errore
                JOptionPane.showMessageDialog(panel,
                        "Error: A task with the same name already exists.",
                        "Update Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ricarica la vista della scrivania
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            }

            // Refresh
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
