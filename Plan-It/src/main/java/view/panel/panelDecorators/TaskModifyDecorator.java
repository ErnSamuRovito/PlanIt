package view.panel.panelDecorators;

import controller.commandPattern.ModifyTaskCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.GlobalResources;
import model.dao.task.TaskDAOImpl;
import core.SqLiteConnection;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class TaskModifyDecorator extends CreatePanelDecorator {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField nameTaskField;
    private CustomTextPane descriptionTaskPane;
    private CustomDataPicker customDataPicker;
    private CustomComboBox<String> comboBox;
    private CustomButton modifyButton;
    private CustomLabel backLabel;

    private final String taskTitle, user, folder;
    private String title, description, dueDate;
    private int urgency;

    public TaskModifyDecorator(CreatePanel createPanel, String taskTitle, String user, String folder) {
        super(createPanel);
        this.taskTitle = taskTitle;
        this.user = user;
        this.folder = folder;

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        GridBagConstraints gbc = createGridBagConstraints();

        loadTaskData();
        buildComponents();
        addComponentsToPanel(gbc);
    }

    @Override
    public void buildPanel() {
        super.buildPanel();
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    private void buildComponents() {
        nameTaskField = buildNameTaskField();
        descriptionTaskPane = buildDescriptionTaskPane();
        customDataPicker = buildCustomDataPicker();
        comboBox = buildComboBox();
        modifyButton = buildModifyButton();
        backLabel = createBackLabel();
    }

    private CustomTextField buildNameTaskField() {
        UIBuilder builder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(builder);
        builder.text(title).size(FIELD_SIZE).placeholder("");
        UIComponentFactory factory = new CustomTextFieldFactory(builder);
        return (CustomTextField) factory.orderComponent(builder);
    }

    private CustomTextPane buildDescriptionTaskPane() {
        UIBuilder builder = new CustomTextPaneBuilder();
        builder.content(description)
                .size(new Dimension(500, 200))
                .editable(true)
                .backgroundColor(GlobalResources.COLOR_WHITE);
        UIComponentFactory factory = new CustomTextPaneFactory(builder);
        return (CustomTextPane) factory.orderComponent(builder);
    }

    private CustomDataPicker buildCustomDataPicker() {
        CustomDataPicker picker = new CustomDataPicker();

        // Verifica se dueDate è nullo e imposta una data predefinita se necessario
        if (dueDate != null && !dueDate.isEmpty()) {
            picker.setSelectedDate(dueDate);
        } else {
            // Imposta una data predefinita (es. data corrente)
            picker.setSelectedDate("01/01/2025");  // Esempio di data predefinita
        }

        return picker;
    }

    private CustomComboBox<String> buildComboBox() {
        String[] items = {"Low", "Medium", "High"};
        CustomComboBox<String> box = new CustomComboBox<>(items);
        box.setSelectedItem(convertUrgencyToString(urgency));
        return box;
    }

    private CustomButton buildModifyButton() {
        UIBuilder builder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(builder);
        builder.text("Modify Task").size(BUTTON_SIZE).action(new ModifyTaskCommand(this));
        UIComponentFactory factory = new CustomButtonFactory(builder);
        return (CustomButton) factory.orderComponent(builder);
    }

    private CustomLabel createBackLabel() {
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(new GoToDeskViewCommand());
        UIComponentFactory factory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) factory.orderComponent(labelBuilder);
    }

    private void addComponentsToPanel(GridBagConstraints gbc) {
        gbc.gridy = 0;
        add(nameTaskField, gbc);
        gbc.gridy = 1;

        // Wrap the description task pane with a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(descriptionTaskPane);
        scrollPane.setPreferredSize(new Dimension(500, 200)); // Set the size of the scrollable pane
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll

        add(scrollPane, gbc); // Add the scrollable description panel
        gbc.gridy = 2;
        add(customDataPicker, gbc);
        gbc.gridy = 3;
        add(comboBox, gbc);
        gbc.gridy = 4;
        add(modifyButton, gbc);
        gbc.gridy = 5;
        add(backLabel, gbc);
    }

    private void loadTaskData() {
        if (taskTitle == null || taskTitle.isEmpty()) {
            return;
        }

        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            ArrayList<String> task = taskDAO.getTaskDataByTitleAndFolderAndUsername(taskTitle, folder, user);

            if (task != null && task.size() >= 6) {
                title = task.get(1);
                description = task.get(2);
                dueDate = task.get(3);
                urgency = Integer.parseInt(task.get(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertUrgencyToString(int urgency) {
        return switch (urgency) {
            case 0 -> "Low";
            case 1 -> "Medium";
            case 2 -> "High";
            default -> "Low";
        };
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getUrgency() {
        return urgency;
    }

    public String getNewTitle() {
        return nameTaskField.getText();
    }

    public String getNewDescription() {
        return descriptionTaskPane.getText();
    }

    public String getNewDueDate() {
        return customDataPicker.getSelectedDate();
    }

    public int getNewUrgency() {
        return comboBox.getSelectedIndex();
    }
}
