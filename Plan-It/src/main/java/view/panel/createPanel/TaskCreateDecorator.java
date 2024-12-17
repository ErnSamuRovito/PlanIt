package view.panel.createPanel;

import controller.commandPattern.CreateTaskCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.CustomButtonBuilder;
import view.UICreationalPattern.UIBuilders.CustomTextFieldBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomComboBox;
import view.UICreationalPattern.UIComponents.CustomDataPicker;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import java.awt.*;
import java.util.Objects;

public class TaskCreateDecorator extends CreatePanelDecorator {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField nameTaskField;
    private CustomTextField descriptionTaskField;
    private CustomDataPicker customDataPicker;
    private CustomButton createButton;
    private CustomComboBox<String> comboBox;

    public TaskCreateDecorator(CreatePanel createPanel) {
        super(createPanel);
    }

    @Override
    public void buildPanel() {
        super.buildPanel();

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Task Name Field
        UIBuilder nameTaskFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(nameTaskFieldBuilder);
        nameTaskFieldBuilder.text("Task Name").size(FIELD_SIZE).placeholder("Insert task name");

        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(nameTaskFieldBuilder);
        nameTaskField = (CustomTextField) textFieldFactory.orderComponent(nameTaskFieldBuilder);

        // Task Description Field
        UIBuilder descriptionTaskBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(descriptionTaskBuilder);
        descriptionTaskBuilder.text("Description").size(FIELD_SIZE).placeholder("Insert task description");

        UIComponentFactory descriptionFieldFactory = new CustomTextFieldFactory(descriptionTaskBuilder);
        descriptionTaskField = (CustomTextField) descriptionFieldFactory.orderComponent(descriptionTaskBuilder);

        // Date Picker
        customDataPicker = new CustomDataPicker();

        // Priority ComboBox
        String[] items = {"Low", "Medium", "High"};
        comboBox = new CustomComboBox<>(items);

        // Create New Task Button
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Create New Task")
                .size(BUTTON_SIZE)
                .action(new CreateTaskCommand(this));

        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        createButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);

        gbc.gridy = 0;
        add(nameTaskField, gbc);

        gbc.gridy = 1;
        add(descriptionTaskField, gbc);

        gbc.gridy = 2;
        add(customDataPicker, gbc);

        gbc.gridy = 3;
        add(comboBox, gbc);

        gbc.gridy = 4;
        add(createButton, gbc);
    }

    // Getters and Setters
    public String getNameTaskField() {
        return nameTaskField.getText();
    }

    public String getDescriptionTaskField() {
        return descriptionTaskField.getText();
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
