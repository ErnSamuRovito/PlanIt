package view.panel;

import controller.commandPattern.navigationCommands.GoToChangePasswordCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.commandPattern.userCommand.DeleteUserCommand;
import controller.commandPattern.userCommand.SaveSettingsCommand;
import core.GlobalResources;
import model.User;
import model.plant.AvatarPlant;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField usernameField, namePlantField;
    private CustomButton changePasswordButton, saveButton, deleteUserButton;
    private CustomLabel usernameLabel, namePlantLabel, backLabel;

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

        usernameLabel = createCustomLabel("Username:");
        namePlantLabel = createCustomLabel("Plant name:");
        backLabel = createBackLabel();
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

    private CustomLabel createCustomLabel(String text){
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(labelBuilder);
        labelBuilder.size(FIELD_SIZE).text(text);
        UIComponentFactory factory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) factory.orderComponent(labelBuilder);
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
                .backgroundColor(GlobalResources.COLOR_RED1)
                .hoverBackgroundColor(GlobalResources.COLOR_RED2)
                .pressedBackgroundColor(GlobalResources.COLOR_RED1)
                .action(new DeleteUserCommand(User.getInstance().getId()));

        UIComponentFactory factory = new CustomButtonFactory(buttonBuilder);
        return (CustomButton) factory.orderComponent(buttonBuilder);
    }

    private CustomLabel createBackLabel(){
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(new GoToDeskViewCommand());
        UIComponentFactory factory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) factory.orderComponent(labelBuilder);
    }

    private void addComponentsToPanel() {
        GridBagConstraints gbc = createGridBagConstraints();

        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridy = 1;
        add(usernameField, gbc);
        gbc.gridy = 2;
        add(namePlantLabel, gbc);
        gbc.gridy = 3;
        add(namePlantField, gbc);
        gbc.gridy = 4;
        add(changePasswordButton, gbc);
        gbc.gridy = 5;
        add(saveButton, gbc);
        gbc.gridy = 6;
        add(deleteUserButton, gbc);

        gbc.gridy = 7;
        add(backLabel, gbc);
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }
}
