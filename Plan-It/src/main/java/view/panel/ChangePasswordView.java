package view.panel;

import controller.commandPattern.ChangePasswordCommand;
import controller.commandPattern.GoToDeskViewCommand;
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

    private CustomPasswordField newPasswordTextField;
    private CustomPasswordField confirmPasswordTextField;
    private CustomButton savePasswordButton;
    private CustomLabel backLabel;

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
        newPasswordTextField = createNewPasswordTextField();
        confirmPasswordTextField = createConfirmPasswordButton();
        savePasswordButton = createSaveButton();
        backLabel = createBackLabel();
    }

    private CustomPasswordField createNewPasswordTextField() {
        UIBuilder textFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardTextField(textFieldBuilder);
        textFieldBuilder.size(FIELD_SIZE).placeholder("New Password");

        UIComponentFactory factory = new CustomTextFieldFactory(textFieldBuilder);
        return (CustomPasswordField) factory.orderComponent(textFieldBuilder);
    }

    private CustomPasswordField createConfirmPasswordButton() {
        UIBuilder textFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardTextField(textFieldBuilder);
        textFieldBuilder.size(FIELD_SIZE).placeholder("Confirm Password");;

        UIComponentFactory factory = new CustomTextFieldFactory(textFieldBuilder);
        return (CustomPasswordField) factory.orderComponent(textFieldBuilder);
    }

    private CustomButton createSaveButton() {
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder
                .text("Save")
                .size(BUTTON_SIZE)
                .action(new ChangePasswordCommand(newPasswordTextField, confirmPasswordTextField));

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
        add(newPasswordTextField, gbc);

        gbc.gridy = 1;
        add(confirmPasswordTextField, gbc);

        gbc.gridy = 2;
        add(savePasswordButton, gbc);

        gbc.gridy = 3;
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
