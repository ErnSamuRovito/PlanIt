package view.panel.createPannel;

import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.CustomButtonBuilder;
import view.UICreationalPattern.UIBuilders.CustomTextFieldBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import java.awt.*;

public class FolderCreateDecorator extends CreatePanelDecorator{
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    public FolderCreateDecorator(CreatePanel createPanel) {
        super(createPanel);
    }

    @Override
    public void buildPanel() {
        super.buildPanel(); // Call the buildPanel method of the base class

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins for components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create task name text field using Builder and Factory ----------------
        UIBuilder nameTaskFieldBuilder = new CustomTextFieldBuilder();
        UIDirector uiDirector = new UIDirector();
        uiDirector.buildStandardTextField(nameTaskFieldBuilder);
        nameTaskFieldBuilder.text("Folder").size(FIELD_SIZE).placeholder("Insert name folder");

        // Use factory to create the text field
        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(nameTaskFieldBuilder);
        CustomTextField nameTaskField = (CustomTextField) textFieldFactory.orderComponent(nameTaskFieldBuilder);

        // Create "Create new task" button using Builder and Factory ----------------
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        uiDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Create new folder").size(BUTTON_SIZE);

        // buttonBuilder.action(new SigninCommand(this));

        // Use factory to create the button
        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        CustomButton createButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);

        gbc.gridy = 0;
        add(nameTaskField, gbc);

        // Add button and text field to panel
        gbc.gridy = 1;
        add(createButton, gbc);
    }
}
