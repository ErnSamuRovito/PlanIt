package view.panel.panelDecorators;

import controller.commandPattern.CreateTaskCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class TaskCreateDecorator extends CreatePanelDecorator {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField nameTaskField;
    private CustomTextPane descriptionTaskPane;
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
        nameTaskFieldBuilder.size(FIELD_SIZE).placeholder("Insert task name");

        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(nameTaskFieldBuilder);
        nameTaskField = (CustomTextField) textFieldFactory.orderComponent(nameTaskFieldBuilder);

        // Task Description Field (Scrollable TextPane)
        UIBuilder descriptionTaskBuilder = new CustomTextPaneBuilder();
        descriptionTaskBuilder.size(FIELD_SIZE)
                .placeholder("Insert task description")
                .editable(true)
                .size(new Dimension(500, 200))
                .backgroundColor(GlobalResources.COLOR_WHITE);
        UIComponentFactory descriptionTextFieldFactory = new CustomTextPaneFactory(descriptionTaskBuilder);
        descriptionTaskPane = (CustomTextPane) descriptionTextFieldFactory.orderComponent(descriptionTaskBuilder);

        // Wrap the description task pane with a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(descriptionTaskPane);
        scrollPane.setPreferredSize(new Dimension(500, 200)); // Set the size of the scrollable pane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll

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

        // Create "Back" clickable label
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(
                new GoToDeskViewCommand(ComponentManager.getInstance().getUser(), ComponentManager.getInstance().getCurrFolder())
        );
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        CustomLabel backLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);

        gbc.gridy = 0;
        add(nameTaskField, gbc);
        gbc.gridy = 1;
        add(scrollPane, gbc); // Add the scrollable description panel
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
