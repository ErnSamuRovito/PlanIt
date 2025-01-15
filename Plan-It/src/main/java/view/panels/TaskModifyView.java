package view.panels;

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
    private final ArrayList<String> taskData;

    public TaskModifyView(String title, String user, String startFolder) {
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);
        this.taskData = taskController.getTaskData(title, user, startFolder);

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        builders.add(createLabelBuilder(registry, "Title"));              // 0
        builders.add(createTextFieldBuilder(registry, taskData.get(1)));   // 1
        builders.add(createTextPaneBuilder(registry, taskData.get(2)));    // 2
        builders.add(createComboBoxBuilder(registry, taskData.get(4)));    // 3
        builders.add(createDatePickerBuilder(registry, taskData.get(3)));  // 4
        builders.add(createButtonBuilder(registry));                       // 5
        builders.add(createBackLabelBuilder(registry));                    // 6
    }

    private UIBuilder createLabelBuilder(UIComponentFactoryRegistry registry, String text) {
        return registry.getFactory("Label").createBuild().text(text);
    }

    private UIBuilder createTextFieldBuilder(UIComponentFactoryRegistry registry, String text) {
        return registry.getFactory("TextField").createBuild().text(text);
    }

    private UIBuilder createTextPaneBuilder(UIComponentFactoryRegistry registry, String content) {
        return registry.getFactory("TextPane").createBuild().content(content).editable(true);
    }

    private UIBuilder createComboBoxBuilder(UIComponentFactoryRegistry registry, String selectedIndex) {
        return registry.getFactory("ComboBox").createBuild().selectedIndex(Integer.parseInt(selectedIndex));
    }

    private UIBuilder createDatePickerBuilder(UIComponentFactoryRegistry registry, String date) {
        return registry.getFactory("DataPicker").createBuild().date(date);
    }

    private UIBuilder createButtonBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("Button").createBuild().text("Modify").action(new ModifyTaskCommand(this));
    }

    private UIBuilder createBackLabelBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("ClickableLabel").createBuild().text("Back").action(new GoToDeskViewCommand());
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i;

            if (i == 2) {
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
        return taskData.get(1);
    }

    public String getNewTitle() {
        return ((CustomTextField) components.get(1)).getText();
    }

    public String getNewDescription() {
        return ((CustomTextPane) components.get(2)).getText();
    }

    public int getNewUrgency() {
        return ((CustomComboBox) components.get(3)).getSelectedIndex();
    }

    public String getNewDueDate() {
        return ((CustomDatePicker) components.get(4)).getSelectedDate();
    }
}
