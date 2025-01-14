package view.panel.panelDecorators;

import controller.commandPattern.CreateTaskCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactoryHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TaskCreateDecorator extends CreatePanelDecorator {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField nameTaskField;
    private CustomTextPane descriptionTaskPane;
    private JScrollPane scrollPane;
    private CustomDatePicker customDataPicker;
    private CustomButton createButton;
    private CustomComboBox<String> comboBox;
    private CustomLabel backLabel;

    public TaskCreateDecorator(CreatePanel createPanel) {
        super(createPanel);
    }

    @Override
    public void buildPanel() {
        super.buildPanel();

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        createComponents();
        addComponentsToPanel();
    }

    private void createComponents(){
        // Task Name Field
        nameTaskField = UIFactoryHelper.createTextField("", "Insert task name");
        // Task Description Field (Scrollable TextPane)
        descriptionTaskPane = UIFactoryHelper.createEditableTextPane("");
        // Wrap il description task pane in uno JScrollPane
        scrollPane = UIFactoryHelper.createScrollPane(descriptionTaskPane, new Dimension(500, 200));
        // Date Picker
        //customDataPicker = UIFactoryHelper.createDataPicker(null);
        // Priority ComboBox
        String[] items = {"Low", "Medium", "High"};
        //comboBox = UIFactoryHelper.createComboBox(items, 0);
        // Create New Task Button
        createButton = UIFactoryHelper.createButton("Create New Task", new CreateTaskCommand(this));
        // Create "Back" clickable label
        backLabel = UIFactoryHelper.createClickableLabel("Back",
                new GoToDeskViewCommand(ComponentManager.getInstance().getUser(), ComponentManager.getInstance().getCurrFolder()));
    }

    private void addComponentsToPanel(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        add(nameTaskField, gbc);
        gbc.gridy = 1;
        add(scrollPane, gbc);
        gbc.gridy = 2;
        add(customDataPicker, gbc);
        gbc.gridy = 3;
        add(comboBox, gbc);
        gbc.gridy = 4;
        add(createButton, gbc);
        gbc.gridy = 5;
        add(backLabel, gbc);
    }

    // Getters and Setters
    public String getNameTaskField() {
        return nameTaskField.getText();
    }

    public String getDescriptionTaskPane() {
        return descriptionTaskPane.getText();
    }

    public String getCustomDataPicker() {
        return customDataPicker.getSelectedDate();
    }

    public int getComboBoxSelection() {
        String selectedItem = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        return switch (selectedItem) {
            case "Low" -> 1;
            case "Medium" -> 2;
            case "High" -> 3;
            default -> -1;
        };
    }
}
