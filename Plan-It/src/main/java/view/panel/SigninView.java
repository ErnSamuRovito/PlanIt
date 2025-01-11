package view.panel;

import controller.commandPattern.navigationCommands.GoToLoginCommand;
import controller.commandPattern.userCommand.SigninCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactoryHelper;

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
        createComponents();

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

    private void createComponents(){
        // Creazione dei componenti tramite UIFactoryHelper
        usernameField = UIFactoryHelper.createTextField("", "Username");
        emailField = UIFactoryHelper.createTextField("", "Email");
        passwordField = UIFactoryHelper.createPasswordField("Password");
        confirmPasswordField = UIFactoryHelper.createPasswordField("Confirm Password");
        registerButton = UIFactoryHelper.createButton("Register", new SigninCommand(this));
        loginLabel = UIFactoryHelper.createClickableLabel("Already have an account? Log in!", new GoToLoginCommand());

        // Creazione dei label
        usernameLabel = UIFactoryHelper.createLabel("Username:");
        emailLabel = UIFactoryHelper.createLabel("Email:");
        passwordLabel = UIFactoryHelper.createLabel("Password:");
        confirmPasswordLabel = UIFactoryHelper.createLabel("Confirm Password:");
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
