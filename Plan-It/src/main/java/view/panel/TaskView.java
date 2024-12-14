package view.panel;

import core.DatabaseTaskDataLoader;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;

public class TaskView extends JPanel {
    private final DatabaseTaskDataLoader databaseTaskDataLoader;
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    private JPanel mainPanel;

    public TaskView(DatabaseTaskDataLoader databaseTaskDataLoader){
        this.databaseTaskDataLoader = databaseTaskDataLoader;

        mainPanel=new JPanel(new GridBagLayout());
        showData();
        add(mainPanel);
    }

    private void showData(){
        for (String s : databaseTaskDataLoader.getTaskData())
            System.out.println(s);

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins for components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create task name text field using Builder and Factory ----------------
        UIBuilder titleLabelBuilder = new CustomLabelBuilder();
        UIDirector uiDirector = new UIDirector();
        uiDirector.buildStandardLabel(titleLabelBuilder);
        titleLabelBuilder.text(databaseTaskDataLoader.getTaskData().get(0)).size(FIELD_SIZE);

        UIComponentFactory titleLabelFactory = new CustomLabelFactory(titleLabelBuilder);
        CustomLabel titleLabel = (CustomLabel) titleLabelFactory.orderComponent(titleLabelBuilder);

        gbc.gridy = 0;
        mainPanel.add(titleLabel,gbc);
    }
}
