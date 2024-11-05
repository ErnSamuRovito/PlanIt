package view;

import controller.LoginButtonController;
import view.factory.ButtonFactory;
import view.factory.TextFieldFactory;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private CustomTextField usernameField;
    private CustomTextField passwordField;

    public LoginView() {
        initializeUI();
    }

    private void initializeUI()
    {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campo "Nickname"
        JLabel usernameLabel = new JLabel("Nickname:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new TextFieldFactory()
                .setSize(new Dimension(200, 30))
                .create();
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameField, gbc);

        // Campo "Password"
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new TextFieldFactory()
                .setSize(new Dimension(200, 30))
                .setIsPassword(true)
                .create();
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(passwordField, gbc);

        // Pulsante di login
        CustomButton loginButton = new ButtonFactory("Accedi")
                .setSize(new Dimension(100, 50))
                .setActionListener(new LoginButtonController())
                .create();

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(loginButton, gbc);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }
}
