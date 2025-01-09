package view.panel;

import controller.commandPattern.navigationCommands.GoToChangePasswordCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.commandPattern.userCommand.DeleteUserCommand;
import controller.commandPattern.userCommand.SaveSettingsCommand;
import core.GlobalResources;
import model.User;
import model.plant.AvatarPlant;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextField;

import javax.swing.*;
import java.awt.*;

public class SettingsView extends JPanel {

    private CustomTextField usernameField, namePlantField, emailField; // Aggiunta la field per l'email
    private CustomButton changePasswordButton, saveButton, deleteUserButton;
    private CustomLabel usernameLabel, namePlantLabel, emailLabel, backLabel; // Aggiunta l'etichetta per l'email

    public SettingsView() {
        initializeLayout();
        createComponents();
        addComponentsToPanel();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
    }

    private void createComponents() {
        // Using UIFactoryHelper for component creation
        usernameField = UIFactoryHelper.createTextField("",User.getInstance().getUsername());
        emailField = UIFactoryHelper.createTextField("",User.getInstance().getEmail()); // Initialize email field
        changePasswordButton = UIFactoryHelper.createButton("Change password", new GoToChangePasswordCommand());
        namePlantField = UIFactoryHelper.createTextField("",AvatarPlant.getInstance().getName());
        saveButton = UIFactoryHelper.createButton("Save", new SaveSettingsCommand(usernameField, namePlantField, emailField));
        deleteUserButton = UIFactoryHelper.createAlertButton("Delete User", new DeleteUserCommand(User.getInstance().getId()));

        usernameLabel = UIFactoryHelper.createLabel("Username:");
        emailLabel = UIFactoryHelper.createLabel("Email:"); // Label for email
        namePlantLabel = UIFactoryHelper.createLabel("Plant name:");
        backLabel = UIFactoryHelper.createClickableLabel("Back", new GoToDeskViewCommand());
    }

    private void addComponentsToPanel() {
        GridBagConstraints gbc = createGridBagConstraints();

        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Add new components for email
        gbc.gridy = 2;
        add(emailLabel, gbc); // Email label
        gbc.gridy = 3;
        add(emailField, gbc); // Email field

        gbc.gridy = 4;
        add(namePlantLabel, gbc);
        gbc.gridy = 5;
        add(namePlantField, gbc);
        gbc.gridy = 6;
        add(changePasswordButton, gbc);
        gbc.gridy = 7;
        add(saveButton, gbc);
        gbc.gridy = 8;
        add(deleteUserButton, gbc);

        gbc.gridy = 9;
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
