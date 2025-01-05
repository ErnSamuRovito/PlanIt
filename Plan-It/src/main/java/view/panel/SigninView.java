package view.panel;

import controller.commandPattern.navigationCommands.GoToLoginCommand;
import controller.commandPattern.userCommand.SigninCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import javax.swing.*;
import java.awt.*;

public class SigninView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    private static final Dimension LABEL_SIZE = new Dimension(150, 15);

    private CustomTextField usernameField;
    private CustomTextField emailField;
    private CustomPasswordField passwordField;
    private CustomPasswordField confirmPasswordField;
    private CustomButton registerButton;
    private CustomLabel loginLabel;
    private CustomLabel usernameLabel, emailLabel, passwordLabel, confirmPasswordLabel;

    public SigninView() {
        // Imposta layout e margini del pannello
        setupLayout();

        // Creazione dei componenti
        createUsernameField();
        createEmailField();
        createPasswordField();
        createConfirmPasswordField();
        createRegisterButton();
        createLoginLabel();

        // Creazione dei label
        usernameLabel = createLabel("Username:");
        emailLabel = createLabel("Email:");
        passwordLabel = createLabel("Password:");
        confirmPasswordLabel = createLabel("Confirm Password:");  // Nuovo label

        // Aggiungi componenti al pannello
        addComponentsToPanel();
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        // Configura i margini per i componenti
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
    }

    private CustomLabel createLabel(String text){
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(labelBuilder);
        labelBuilder.text(text).size(LABEL_SIZE);
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    private void createUsernameField() {
        // Creazione del campo di testo per lo username
        UIBuilder textFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(textFieldBuilder);
        textFieldBuilder.size(FIELD_SIZE).placeholder("Username");

        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(textFieldBuilder);
        usernameField = (CustomTextField) textFieldFactory.orderComponent(textFieldBuilder);
    }

    private void createEmailField() {
        // Creazione del campo di testo per l'email
        UIBuilder textFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(textFieldBuilder);
        textFieldBuilder.size(FIELD_SIZE).placeholder("Email");

        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(textFieldBuilder);
        emailField = (CustomTextField) textFieldFactory.orderComponent(textFieldBuilder);
    }

    private void createPasswordField() {
        // Creazione del campo di testo per la password
        UIBuilder passwordFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardPasswordField(passwordFieldBuilder);
        passwordFieldBuilder.size(FIELD_SIZE).placeholder("Password");

        UIComponentFactory passwordFieldFactory = new CustomTextFieldFactory(passwordFieldBuilder);
        passwordField = (CustomPasswordField) passwordFieldFactory.orderComponent(passwordFieldBuilder);
    }

    private void createConfirmPasswordField() {
        // Creazione del campo di testo per la conferma della password
        UIBuilder confirmPasswordFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardPasswordField(confirmPasswordFieldBuilder);
        confirmPasswordFieldBuilder.size(FIELD_SIZE).placeholder("Confirm Password");

        UIComponentFactory confirmPasswordFieldFactory = new CustomTextFieldFactory(confirmPasswordFieldBuilder);
        confirmPasswordField = (CustomPasswordField) confirmPasswordFieldFactory.orderComponent(confirmPasswordFieldBuilder);
    }

    private void createRegisterButton() {
        // Creazione del pulsante per registrarsi
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Register").size(BUTTON_SIZE).action(new SigninCommand(this));

        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        registerButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);
    }

    private void createLoginLabel() {
        // Creazione del link per il login
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text("Already have an account? Log in!").size(BUTTON_SIZE).action(new GoToLoginCommand());

        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        loginLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    private void addComponentsToPanel() {
        // Aggiungi componenti al pannello con i giusti vincoli di layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0; add(usernameLabel, gbc);
        gbc.gridy = 1; add(usernameField, gbc);
        gbc.gridy = 2; add(emailLabel, gbc);
        gbc.gridy = 3; add(emailField, gbc);
        gbc.gridy = 4; add(passwordLabel, gbc);
        gbc.gridy = 5; add(passwordField, gbc);
        gbc.gridy = 6; add(confirmPasswordLabel, gbc);
        gbc.gridy = 7; add(confirmPasswordField, gbc);
        gbc.gridy = 8; add(registerButton, gbc);
        gbc.gridy = 9; add(loginLabel, gbc);
    }

    // Getter per i campi di input
    public CustomTextField getUsernameField() {
        return usernameField;
    }

    public CustomTextField getEmailField() {
        return emailField;
    }

    public CustomPasswordField getPasswordField() {
        return passwordField;
    }

    public CustomPasswordField getConfirmPasswordField() {
        return confirmPasswordField;
    }
}
