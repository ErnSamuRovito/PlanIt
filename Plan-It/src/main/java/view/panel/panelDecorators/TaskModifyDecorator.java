package view.panel.panelDecorators;

import controller.commandPattern.ModifyTaskCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.GlobalResources;
import model.dao.task.TaskDAOImpl;
import core.SqLiteConnection;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactoryHelper;

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

        loadTaskData();
        createComponents();  // Separate method to create components
        addComponentsToPanel();  // Add components to the panel
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

    // Separated the component creation logic here
    private void createComponents() {
        nameTaskField = UIFactoryHelper.createTextField(title != null ? title : "", "");
        descriptionTaskPane = UIFactoryHelper.createEditableTextPane(description);
        customDataPicker = UIFactoryHelper.createDataPicker(dueDate != null && !dueDate.isEmpty() ? dueDate : null);
        if (urgency>2) urgency = 2;
        if (urgency<0) urgency = 0;
        comboBox = UIFactoryHelper.createComboBox(new String[]{"Low", "Medium", "High"}, urgency);
        modifyButton = UIFactoryHelper.createButton("Modify Task", new ModifyTaskCommand(this));
        backLabel = UIFactoryHelper.createClickableLabel("Back", new GoToDeskViewCommand());
    }

    // Add all components to the panel
    private void addComponentsToPanel() {
        GridBagConstraints gbc = createGridBagConstraints();
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
