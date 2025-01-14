package view.panel;

import controller.commandPattern.CreateTaskCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomComboBox;
import view.UICreationalPattern.UIComponents.CustomDatePicker;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIComponents.CustomTextPane;

import javax.swing.*;
import java.awt.*;

public class TaskCreateView extends TemplateView {

    public TaskCreateView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildLabelTitle = registry.getFactory("Label").createBuild();
        buildLabelTitle.text("Title");
        UIBuilder buildTextFieldTitle = registry.getFactory("TextField").createBuild();
        buildTextFieldTitle.placeholder("Title");

        UIBuilder buildTextPane = registry.getFactory("TextPane").createBuild();
        buildTextPane.editable(true);

        UIBuilder buildDataPicker = registry.getFactory("DataPicker").createBuild();

        UIBuilder buildComboBox = registry.getFactory("ComboBox").createBuild();
        buildComboBox.selectedIndex(0);

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("Create")
                .action(new CreateTaskCommand(this));

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

    public String getTitle(){return ((CustomTextField) components.get(1)).getText();}
    public String getDescription(){return ((CustomTextPane) components.get(2)).getText();}
    public int getUrgency(){return ((CustomComboBox) components.get(3)).getSelectedIndex();}
    public String getDate(){return ((CustomDatePicker) components.get(4)).getSelectedDate();}
}