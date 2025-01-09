package view.panel;

import controller.commandPattern.navigationCommands.GoToSigninCommand;
import controller.commandPattern.userCommand.LoginCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIComponents.*;

import java.awt.*;
import javax.swing.*;

public class LoginView extends JPanel {
    private CustomTextField userField;
    private CustomPasswordField passwordField;
    private CustomButton loginButton;
    private CustomLabel signinLabel;
    private CustomLabel usernameLabel;
    private CustomLabel passwordLabel;

    public LoginView() {
        // Impostazioni di layout e margini
        setupLayout();

        // Creazione dei componenti
        createComponents();

        // Aggiungi componenti al pannello
        addComponentsToPanel();
    }

    private void setupLayout() {
        // Imposta layout e margini del pannello
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
    }

    private void createComponents(){
        userField = UIFactoryHelper.createTextField("","Username or Email");
        passwordField = UIFactoryHelper.createPasswordField("Password");
        loginButton = UIFactoryHelper.createButton("Login", new LoginCommand(this));
        signinLabel = UIFactoryHelper.createClickableLabel("Don't have an account? Sign up!", new GoToSigninCommand());
        usernameLabel = UIFactoryHelper.createLabel("Username or Email: ");
        passwordLabel = UIFactoryHelper.createLabel("Password: ");
    }

    private void addComponentsToPanel() {
        // Aggiungi i componenti al pannello con i giusti vincoli di layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        add(usernameLabel, gbc);
        gbc.gridy = 1;
        add(userField, gbc);
        gbc.gridy = 2;
        add(passwordLabel, gbc);
        gbc.gridy = 3;
        add(passwordField, gbc);
        gbc.gridy = 4;
        add(loginButton, gbc);
        gbc.gridy = 5;
        add(signinLabel, gbc);
    }

    // Getter per i campi di input
    public CustomTextField getUserField() {
        return userField;
    }

    public CustomPasswordField getPasswordField() {
        return passwordField;
    }
}
