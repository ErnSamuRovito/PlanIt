package controller.controllers;

import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import view.panel.DeskView;
import view.iconPanel.IconFactory;
import controller.commandPattern.ExploreFolderCommand;
import controller.commandPattern.navigationCommands.GoToTaskViewCommand;

import java.sql.Connection;
import java.sql.SQLException;

public class DeskController {
    private final DeskView view;
    private final String user;
    private final String startFolder;

    public DeskController(DeskView view, String user, String startFolder) {
        this.view = view;
        this.user = user;
        this.startFolder = startFolder;
        displayFoldersAndTasks();
    }

    private void displayFoldersAndTasks() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            for (String folder : folderDAO.getFoldersByFolderAndUser(startFolder, user)) {
                view.addIconPanel(
                        IconFactory.createIconPanel("folder", folder, new ExploreFolderCommand(user, folder))
                );
            }

            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            for (String task : taskDAO.getTasksByFolderAndUser(startFolder, user)) {
                String taskState = determineTaskState(taskDAO, task);
                view.addIconPanel(
                        IconFactory.createIconPanel(taskState, task, new GoToTaskViewCommand(task, user, startFolder))
                );
            }

            // Aggiunge il pulsante "New" dopo cartelle e task
            view.addCreateIcon();
            view.addPopupMenu();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String determineTaskState(TaskDAOImpl taskDAO, String task) {
        int taskStatus = taskDAO.checkTaskByFolderAndTitle(startFolder, user, task);
        return switch (taskStatus) {
            case 100 -> "taskCompleted";
            case -1 -> "taskExpired";
            default -> "task";
        };
    }
}
