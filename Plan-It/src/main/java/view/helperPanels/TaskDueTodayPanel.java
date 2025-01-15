package view.helperPanels;

import controller.commandPattern.navigationCommands.GoToTaskViewCommand;
import controller.controllers.TaskController;
import core.ComponentManager;
import core.GlobalResources;
import model.composite.Task;
import model.services.TaskService;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.panels.TemplateView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskDueTodayPanel extends TemplateView {
    private ArrayList<Task> tasks;
    private TaskController taskController;

    public TaskDueTodayPanel() {
        taskController = new TaskController(new TaskService());
        String username = ComponentManager.getInstance().getUser();
        tasks = taskController.getTasksDueToday(username);
        initialize();
        this.setBackground(GlobalResources.COLOR_WHITE);
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        // Creazione dell'etichetta "Folder name"
        UIBuilder taskDueTodayLabelBuilder = registry.getFactory("Label").createBuild();
        taskDueTodayLabelBuilder.text("Task due today:").textColor(Color.BLACK);
        builders.add(taskDueTodayLabelBuilder);

        for (Task task : tasks) {
            UIBuilder taskLabelBuilder = registry.getFactory("ClickableLabel").createBuild();
            taskLabelBuilder
                    .text(task.getTitle())
                    .action(
                            new GoToTaskViewCommand(
                                    task.getTitle(),
                                    ComponentManager.getInstance().getUser(),
                                    //ComponentManager.getInstance().getCurrFolder() //--> NON VA BENE
                                    //task.getFolder() --> name
                                    taskController.getFolderNameByTaskTitle(task.getTitle())
                            )
                    );
            builders.add(taskLabelBuilder);
        }
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i;
            add((Component) components.get(i), gbc);
        }
    }
}
