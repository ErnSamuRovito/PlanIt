package view.panel;

import controller.RegisterButtonController;
import controller.ToLoginController;
import core.GlobalResources;
import view.UI.CustomButton;
import view.UI.CustomClickableLabel;
import view.UI.CustomTextField;
import view.factory.ButtonFactory;
import view.factory.ClickableLabelFactory;
import view.factory.TextFieldFactory;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(100, 50);

    private final CustomTextField usernameField;
    private final CustomTextField emailField;
    private final CustomTextField passwordField;
    private final CustomClickableLabel loginLabel;

    public RegisterView() {
        this.usernameField = createUsernameField();
        this.emailField = createEmailField();
        this.passwordField = createPasswordField();
        this.loginLabel = createClickableLabel();
        initializeUI();
    }

    private void initializeUI() {
        setBackground(GlobalResources.COLOR_PANNA);
        setLayout(new GridBagLayout());

        addComponent(createTitleLabel(), 0);

        addComponent(new JLabel("Username:"), 1);
        addComponent(usernameField, 2);

        addComponent(new JLabel("Email:"), 3);
        addComponent(emailField, 4);

        addComponent(new JLabel("Password:"), 5);
        addComponent(passwordField, 6);

        addComponent(createRegisterButton(), 7);
        addComponent(loginLabel, 8);
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return titleLabel;
    }

    private CustomTextField createUsernameField() {
        return new TextFieldFactory()
                .setSize(FIELD_SIZE)
                .create();
    }

    private CustomTextField createEmailField() {
        return new TextFieldFactory()
                .setSize(FIELD_SIZE)
                .create();
    }

    private CustomTextField createPasswordField() {
        return new TextFieldFactory()
                .setSize(FIELD_SIZE)
                .setIsPassword(true)
                .create();
    }

    private CustomButton createRegisterButton() {
        return new ButtonFactory("Register")
                .setSize(BUTTON_SIZE)
                .setActionListener(new RegisterButtonController())
                .create();
    }

    private CustomClickableLabel createClickableLabel() {
        return new ClickableLabelFactory("Already have an account? Log in")
                .setSize(BUTTON_SIZE)
                .setMouseListener(new ToLoginController())
                .setLabelColor(GlobalResources.COLOR_GREEN_1)
                .create();
    }

    private void addComponent(Component component, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        add(component, gbc);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }
}
