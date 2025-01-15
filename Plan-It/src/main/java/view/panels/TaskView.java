package view.panels;

import controller.commandPattern.TaskDoneCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.controllers.TaskController;
import model.services.TaskService;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIBuilders.UIBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskView extends TemplateView {
    private final ArrayList<String> taskData;

    public TaskView(String title, String user, String currFolder) {
        // Estrae i dati del task usando il TaskController
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);
        taskData = taskController.getTaskData(title, user, currFolder);

        /* Indici dei dati:
           ("id_task")       0
           ("title")         1
           ("description")   2
           ("due_date")      3
           ("urgency")       4
           ("folder")        5
           ("state")         6
           ("type")          7
           ("extra_info")    8
        */

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        builders.add(createLabelBuilder(registry, taskData.get(1))); // 0: Title
        builders.add(createTextPaneBuilder(registry, taskData.get(2))); // 1: Description

        if (isTaskPending()) {
            builders.add(createDoneButtonBuilder(registry)); // 2: Done button (if applicable)
        }

        builders.add(createBackLabelBuilder(registry)); // 3: Back button
    }

    private UIBuilder createLabelBuilder(UIComponentFactoryRegistry registry, String text) {
        return registry.getFactory("Label").createBuild().text(text);
    }

    private UIBuilder createTextPaneBuilder(UIComponentFactoryRegistry registry, String content) {
        return registry.getFactory("TextPane").createBuild().content(content).editable(false);
    }

    private UIBuilder createDoneButtonBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("Button").createBuild()
                .text("DONE!")
                .action(new TaskDoneCommand(taskData.get(0)));
    }

    private UIBuilder createBackLabelBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("ClickableLabel").createBuild()
                .text("Back")
                .action(new GoToDeskViewCommand());
    }

    private boolean isTaskPending() {
        int taskState = Integer.parseInt(taskData.get(6));
        return taskState != -1 && taskState != 100;
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);

        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i;

            if (i == 1) { // Add description with JScrollPane
                addComponentWithScrollPane(components.get(i));
            } else {
                add((Component) components.get(i), gbc);
            }
        }
    }

    private void addComponentWithScrollPane(Object component) {
        JScrollPane scrollPane = new JScrollPane((Component) component);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, gbc);
    }
}
