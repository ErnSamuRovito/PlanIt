package view.panel;

import controller.LoginButtonController;
import controller.ToRegisterController;
import core.GlobalResources;
import view.UI.CustomButton;
import view.UI.CustomClickableLabel;
import view.UI.CustomTextField;
import view.factory.ButtonFactory;
import view.factory.ClickableLabelFactory;
import view.factory.TextFieldFactory;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(100, 50);

    private final CustomTextField usernameField;
    private final CustomTextField passwordField;

    public LoginView() {
        this.usernameField = createUsernameField();
        this.passwordField = createPasswordField();
        CustomClickableLabel registerLabel = createClickableLabel();
        initializeUI();
    }

    private void initializeUI() {
        setBackground(GlobalResources.COLOR_PANNA);
        setLayout(new GridBagLayout());

        addComponent(createTitleLabel(), 0);

        addComponent(new JLabel("Username:"), 1);
        addComponent(usernameField, 2);

        addComponent(new JLabel("Password:"), 3);
        addComponent(passwordField, 4);

        addComponent(createLoginButton(), 5);
        addComponent(createClickableLabel(), 6);
    }

    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return titleLabel;
    }

    private CustomTextField createUsernameField() {
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

    private CustomButton createLoginButton() {
        return new ButtonFactory("Login")
                .setSize(BUTTON_SIZE)
                .setActionListener(new LoginButtonController())
                .create();
    }

    private CustomClickableLabel createClickableLabel() {
        return new ClickableLabelFactory("No account? Sign up!")
                .setSize(BUTTON_SIZE)
                .setMouseListener(new ToRegisterController())
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

    public String getPassword() {
        return passwordField.getText();
    }
}
