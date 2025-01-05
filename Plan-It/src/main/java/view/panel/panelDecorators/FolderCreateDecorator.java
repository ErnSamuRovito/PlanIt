package view.panel.panelDecorators;

import controller.commandPattern.CreateFolderCommand;
import controller.commandPattern.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import java.awt.*;

public class FolderCreateDecorator extends CreatePanelDecorator {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    CustomTextField nameFolderField;

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

        // Create folder name text field using Builder and Factory
        UIBuilder nameFolderFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(nameFolderFieldBuilder);

        nameFolderFieldBuilder.text("Folder").size(FIELD_SIZE).placeholder("Insert folder name");

        // Use factory to create the text field
        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(nameFolderFieldBuilder);
        nameFolderField = (CustomTextField) textFieldFactory.orderComponent(nameFolderFieldBuilder);

        // Create "Create new folder" button using Builder and Factory
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Create new folder")
                .size(BUTTON_SIZE)
                .action(new CreateFolderCommand(this));

        // Use factory to create the button
        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        CustomButton createButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);

        // Create "Back" clickable label
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(
            new GoToDeskViewCommand(ComponentManager.getInstance().getUser(),ComponentManager.getInstance().getCurrFolder())
        );
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        CustomLabel backLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);

        // Add components to panel
        gbc.gridy = 0; add(nameFolderField, gbc);
        gbc.gridy = 1; add(createButton, gbc);
        gbc.gridy = 2; add(backLabel, gbc);
    }

    public String getTextFieldName() {
        return nameFolderField.getText();
    }
}
