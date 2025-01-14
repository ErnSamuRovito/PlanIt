package view.panel;

import controller.commandPattern.TaskDoneCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.controllers.TaskController;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIBuilders.UIBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskView extends TemplateView {
    private ArrayList<String> taskData;

    public TaskView(String title, String user, String currFolder, TaskController taskController) {
        //estrapola i dati del task usando il TaskController:
        taskData = taskController.getTaskData(title, user, currFolder);
        /*("id_task")       0
          ("title")         1
          ("description")   2
          ("due_date")      3
          ("urgency")       4
          ("folder")        5
          ("state")         6
          ("type")          7
          ("extra_info")    8*/

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildLabelTitle = registry.getFactory("Label").createBuild();
        buildLabelTitle.text(taskData.get(1));

        UIBuilder buildTextPane = registry.getFactory("TextPane").createBuild();
        buildTextPane
                .content(taskData.get(2))
                .editable(false);

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("DONE!")
                .action(new TaskDoneCommand(taskData.get(0)));

        UIBuilder builderBackLabel = registry.getFactory("ClickableLabel").createBuild();
        builderBackLabel
                .text("Back")
                .action(new GoToDeskViewCommand());

        builders.add(buildLabelTitle);
        builders.add(buildTextPane);
        if (Integer.parseInt(taskData.get(6))!=-1 && Integer.parseInt(taskData.get(6))!=100) {
            builders.add(buildButton);
        }
        builders.add(builderBackLabel);
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i;

            if (i == 1) {
                // Recupera il componente al indice 1 (che dovrebbe essere il TextPane)
                Component component = (Component) components.get(i);

                // Creiamo un JScrollPane con il componente
                JScrollPane scrollPane = new JScrollPane(component);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                // Aggiungi lo scrollPane al pannello
                add(scrollPane, gbc);
            } else {
                // Aggiungi il componente normalmente per gli altri casi
                add((Component) components.get(i), gbc);
            }
        }
    }
}
