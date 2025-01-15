package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.composite.Task;
import model.dao.task.TaskDAOImpl;
import view.panels.TaskModifyView;

import javax.swing.*;
import java.sql.Connection;

public class ModifyTaskCommand implements ActionCommand {
    private final TaskModifyView panel;

    public ModifyTaskCommand(TaskModifyView panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);

            // Otteniamo l'ID del task tramite il titolo, cartella e utente
            int id = taskDAO.getIdByFolderNameAndOwnerAndTitle(
                    ComponentManager.getInstance().getCurrFolderName(),
                    ComponentManager.getInstance().getUser(),
                    panel.getTitle()
            );

            if (id == -1) {
                return;  // Task non trovato
            }

            // Recuperiamo il task dal database usando l'ID
            Task updatedTask = taskDAO.getTaskById(id);
            if (updatedTask == null) {
                return;  // Task non trovato
            }

            // Applichiamo gli aggiornamenti al task
            updatedTask.setTitle(panel.getNewTitle());
            updatedTask.setDescription(panel.getNewDescription());
            updatedTask.setDueDate(panel.getNewDueDate());
            updatedTask.setUrgency(panel.getNewUrgency());

            // Aggiorniamo il task nel database
            boolean success = taskDAO.updateTask(updatedTask, id);

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
