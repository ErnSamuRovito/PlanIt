package view.panel;

import controller.commandPattern.userCommand.ChangePasswordCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIFactoryHelper;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordView extends JPanel {

    private CustomPasswordField currentPasswordField;
    private CustomPasswordField newPasswordTextField;
    private CustomPasswordField confirmPasswordTextField;
    private CustomButton savePasswordButton;
    private CustomLabel backLabel;

    private CustomLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel;

    public ChangePasswordView() {
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
        currentPasswordField = UIFactoryHelper.createPasswordField("Current Password");
        newPasswordTextField = UIFactoryHelper.createPasswordField("New Password");
        confirmPasswordTextField = UIFactoryHelper.createPasswordField("Confirm Password");
        savePasswordButton = UIFactoryHelper.createButton("Save", new ChangePasswordCommand(currentPasswordField, newPasswordTextField, confirmPasswordTextField));
        backLabel = UIFactoryHelper.createClickableLabel("Back", new GoToDeskViewCommand());

        currentPasswordLabel = UIFactoryHelper.createLabel("Current password");
        newPasswordLabel = UIFactoryHelper.createLabel("New password");
        confirmPasswordLabel = UIFactoryHelper.createLabel("Confirm password");
    }

    private void addComponentsToPanel() {
        GridBagConstraints gbc = createGridBagConstraints();

        gbc.gridy = 0;
        add(currentPasswordLabel, gbc);

        gbc.gridy = 1;
        add(currentPasswordField, gbc);

        gbc.gridy = 2;
        add(newPasswordLabel, gbc);

        gbc.gridy = 3;
        add(newPasswordTextField, gbc);

        gbc.gridy = 4;
        add(confirmPasswordLabel, gbc);

        gbc.gridy = 5;
        add(confirmPasswordTextField, gbc);

        gbc.gridy = 6;
        add(savePasswordButton, gbc);

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
