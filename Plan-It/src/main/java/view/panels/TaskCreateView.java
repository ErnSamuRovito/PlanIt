package view.panels;

import controller.commandPattern.componentCommands.CreateTaskCommand;
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

        builders.add(createLabelBuilder(registry, "Title"));          // 0: Title label
        builders.add(createTextFieldBuilder(registry, "Title"));      // 1: Title input
        builders.add(createTextPaneBuilder(registry));                 // 2: Description input
        builders.add(createComboBoxBuilder(registry));                 // 3: Urgency dropdown
        builders.add(createDatePickerBuilder(registry));               // 4: Date picker
        builders.add(createButtonBuilder(registry));                   // 5: Create button
        builders.add(createBackLabelBuilder(registry));                // 6: Back button
    }

    private UIBuilder createLabelBuilder(UIComponentFactoryRegistry registry, String text) {
        return registry.getFactory("Label").createBuild().text(text);
    }

    private UIBuilder createTextFieldBuilder(UIComponentFactoryRegistry registry, String placeholder) {
        return registry.getFactory("TextField").createBuild().placeholder(placeholder);
    }

    private UIBuilder createTextPaneBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("TextPane").createBuild().editable(true);
    }

    private UIBuilder createComboBoxBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("ComboBox").createBuild().selectedIndex(0);
    }

    private UIBuilder createDatePickerBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("DataPicker").createBuild();
    }

    private UIBuilder createButtonBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("Button").createBuild()
                .text("Create")
                .action(new CreateTaskCommand(this));
    }

    private UIBuilder createBackLabelBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("ClickableLabel").createBuild()
                .text("Back")
                .action(new GoToDeskViewCommand());
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);

        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i;

            if (i == 2) { // Add description with JScrollPane
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

    public String getTitle() {
        return ((CustomTextField) components.get(1)).getText();
    }

    public String getDescription() {
        return ((CustomTextPane) components.get(2)).getText();
    }

    public int getUrgency() {
        return ((CustomComboBox) components.get(3)).getSelectedIndex();
    }

    public String getDate() {
        return ((CustomDatePicker) components.get(4)).getSelectedDate();
    }
}
