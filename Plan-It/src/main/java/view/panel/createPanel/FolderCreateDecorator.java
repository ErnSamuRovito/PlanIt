package view.panel.createPanel;

import controller.commandPattern.CreateFolderCommand;
import core.GlobalResources;
import model.User;
import view.UICreationalPattern.UIBuilders.CustomButtonBuilder;
import view.UICreationalPattern.UIBuilders.CustomTextFieldBuilder;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


        // Add components to panel
        gbc.gridy = 0;
        add(nameFolderField, gbc);

        gbc.gridy = 1;
        add(createButton, gbc);
    }

    public String getTextFieldName() {
        return nameFolderField.getText();
    }
}
