package view.panel;

import controller.commandPattern.DeleteUserCommand;
import controller.commandPattern.GoToChangePasswordCommand;
import controller.commandPattern.SaveSettingsCommand;
import core.GlobalResources;
import model.User;
import model.plant.AvatarPlant;
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

public class SettingsView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField usernameField;
    private CustomButton changePasswordButton;
    private CustomTextField namePlantField;
    private CustomButton saveButton;
    private CustomButton deleteUserButton;

    public SettingsView() {
        initializeLayout();
        initializeComponents();
        addComponentsToPanel();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
    }

    private void initializeComponents() {
        usernameField = createCustomTextField(User.getInstance().getUsername());
        changePasswordButton = createCustomButton("Change password");
        namePlantField = createCustomTextField(AvatarPlant.getInstance().getName());
        saveButton = createSaveButton();
        deleteUserButton = createDeleteUserButton();
    }

    private CustomTextField createCustomTextField(String placeholder) {
        UIBuilder textFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(textFieldBuilder);
        textFieldBuilder.size(FIELD_SIZE).placeholder(placeholder);

        UIComponentFactory factory = new CustomTextFieldFactory(textFieldBuilder);
        return (CustomTextField) factory.orderComponent(textFieldBuilder);
    }

    private CustomButton createCustomButton(String text) {
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder
                .text(text)
                .size(BUTTON_SIZE)
                .action(new GoToChangePasswordCommand());

        UIComponentFactory factory = new CustomButtonFactory(buttonBuilder);
        return (CustomButton) factory.orderComponent(buttonBuilder);
    }

    private CustomButton createSaveButton() {
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder
                .text("Save")
                .size(BUTTON_SIZE)
                .action(new SaveSettingsCommand(usernameField, namePlantField));



        UIComponentFactory factory = new CustomButtonFactory(buttonBuilder);
        return (CustomButton) factory.orderComponent(buttonBuilder);
    }

    private CustomButton createDeleteUserButton() {
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder
                .text("Delete User")
                .size(BUTTON_SIZE)
                .action(new DeleteUserCommand(User.getInstance().getId()));

        UIComponentFactory factory = new CustomButtonFactory(buttonBuilder);
        return (CustomButton) factory.orderComponent(buttonBuilder);
    }

    private void addComponentsToPanel() {
        GridBagConstraints gbc = createGridBagConstraints();

        gbc.gridy = 0;
        add(usernameField, gbc);

        gbc.gridy = 1;
        add(changePasswordButton, gbc);

        gbc.gridy = 2;
        add(namePlantField, gbc);

        gbc.gridy = 3;
        add(saveButton, gbc);

        gbc.gridy = 4;
        add(deleteUserButton, gbc);
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }
}
