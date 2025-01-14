package view.panel;

import controller.commandPattern.userCommand.ChangePasswordCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    private static final Dimension LABEL_SIZE = new Dimension(150, 15);

    private static final String CURRENT_PASSWORD_PLACEHOLDER = "Current Password";
    private static final String NEW_PASSWORD_PLACEHOLDER = "New Password";
    private static final String CONFIRM_PASSWORD_PLACEHOLDER = "Confirm Password";
    private static final String SAVE_BUTTON_TEXT = "Save";
    private static final String CURRENT_PASSWORD_LABEL_TEXT = "Current password";
    private static final String NEW_PASSWORD_LABEL_TEXT = "New password";
    private static final String CONFIRM_PASSWORD_LABEL_TEXT = "Confirm password";

    private CustomPasswordField currentPasswordField;
    private CustomPasswordField newPasswordTextField;
    private CustomPasswordField confirmPasswordTextField;
    private CustomButton savePasswordButton;
    private CustomLabel backLabel;

    private CustomLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel;

    public ChangePasswordView() {
        initializeLayout();
        initializeComponents();
        addComponentsToPanel();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
    }

    private void initializeComponents() {
        currentPasswordField = createPasswordField(CURRENT_PASSWORD_PLACEHOLDER);
        newPasswordTextField = createPasswordField(NEW_PASSWORD_PLACEHOLDER);
        confirmPasswordTextField = createPasswordField(CONFIRM_PASSWORD_PLACEHOLDER);
        savePasswordButton = createSaveButton();
        backLabel = createBackLabel();

        currentPasswordLabel = createLabel(CURRENT_PASSWORD_LABEL_TEXT);
        newPasswordLabel = createLabel(NEW_PASSWORD_LABEL_TEXT);
        confirmPasswordLabel = createLabel(CONFIRM_PASSWORD_LABEL_TEXT);
    }

    private CustomLabel createLabel(String text) {
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(labelBuilder);
        labelBuilder.text(text).size(LABEL_SIZE);
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    private CustomPasswordField createPasswordField(String placeholder) {
        UIBuilder textFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardTextField(textFieldBuilder);
        textFieldBuilder.size(FIELD_SIZE).placeholder(placeholder);

        UIComponentFactory factory = new CustomTextFieldFactory(textFieldBuilder);
        return (CustomPasswordField) factory.orderComponent(textFieldBuilder);
    }

    private CustomButton createSaveButton() {
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder
                .text(SAVE_BUTTON_TEXT)
                .size(BUTTON_SIZE)
                .action(new ChangePasswordCommand(currentPasswordField,
                        newPasswordTextField,
                        confirmPasswordTextField));

        UIComponentFactory factory = new CustomButtonFactory(buttonBuilder);
        return (CustomButton) factory.orderComponent(buttonBuilder);
    }

    private CustomLabel createBackLabel() {
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(new GoToDeskViewCommand());
        UIComponentFactory factory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) factory.orderComponent(labelBuilder);
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
