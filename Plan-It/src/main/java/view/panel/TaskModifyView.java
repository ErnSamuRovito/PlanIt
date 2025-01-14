package view.panel;

import controller.commandPattern.ModifyTaskCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.controllers.TaskController;
import model.services.TaskService;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomComboBox;
import view.UICreationalPattern.UIComponents.CustomDatePicker;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIComponents.CustomTextPane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskModifyView extends TemplateView {
    private ArrayList<String> taskData;

    public TaskModifyView(String title,String user,String startFolder) {
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);
        taskData=taskController.getTaskData(title,user,startFolder);

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildLabelTitle = registry.getFactory("Label").createBuild();
        buildLabelTitle.text("Title");
        UIBuilder buildTextFieldTitle = registry.getFactory("TextField").createBuild();
        buildTextFieldTitle.text(taskData.get(1));

        UIBuilder buildTextPane = registry.getFactory("TextPane").createBuild();
        buildTextPane
                .content(taskData.get(2))
                .editable(true);

        UIBuilder buildDataPicker = registry.getFactory("DataPicker").createBuild();
        buildDataPicker.date(taskData.get(3));

        UIBuilder buildComboBox = registry.getFactory("ComboBox").createBuild();
        buildComboBox.selectedIndex(Integer.parseInt(taskData.get(4)));

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("Modify")
                .action(new ModifyTaskCommand(this));

        UIBuilder builderBackLabel = registry.getFactory("ClickableLabel").createBuild();
        builderBackLabel
                .text("Back")
                .action(new GoToDeskViewCommand());

        builders.add(buildLabelTitle);          //0
        builders.add(buildTextFieldTitle);      //1
        builders.add(buildTextPane);            //2
        builders.add(buildComboBox);            //3
        builders.add(buildDataPicker);          //4
        builders.add(buildButton);              //5
        builders.add(builderBackLabel);         //6
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i;

            if (i == 2) {
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

    public String getTitle(){return taskData.get(1);}

    public String getNewTitle(){return ((CustomTextField) components.get(1)).getText();}
    public String getNewDescription(){return ((CustomTextPane) components.get(2)).getText();}
    public int getNewUrgency(){return ((CustomComboBox) components.get(3)).getSelectedIndex();}
    public String getNewDueDate(){return ((CustomDatePicker) components.get(4)).getSelectedDate();}
}
