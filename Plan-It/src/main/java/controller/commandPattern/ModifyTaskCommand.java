package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.task.TaskDAOImpl;
import model.dao.task.TaskDB;
import view.panel.panelDecorators.TaskModifyDecorator;

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
            System.out.println("Task ID found: " + id);

            if (id == -1) {
                System.out.println("Task not found!");
                return;
            }

            // Cerca il task dal database usando l'id
            TaskDB updatedTask = taskDAO.getTaskById(id);
            if (updatedTask == null) {
                System.out.println("Task not retrieved from DB.");
                return;
            }

            // Applica gli aggiornamenti al task
            updatedTask.setTitle(panel.getNewTitle());
            updatedTask.setDescription(panel.getNewDescription());
            updatedTask.setDueDate(panel.getNewDueDate());
            updatedTask.setUrgency(panel.getNewUrgency());

            // Aggiorna il task nel database
            taskDAO.updateTask(updatedTask, id);

            // Refresh
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
