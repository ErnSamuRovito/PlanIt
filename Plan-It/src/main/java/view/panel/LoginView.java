package view.panel;

import controller.commandPattern.navigationCommands.GoToSigninCommand;
import controller.commandPattern.userCommand.LoginCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);
    private static final Dimension LABEL_SIZE = new Dimension(150, 15);

    private CustomTextField userField;
    private CustomPasswordField passwordField;
    private CustomButton loginButton;
    private CustomLabel signinLabel;
    private final CustomLabel usernameLabel;
    private final CustomLabel passwordLabel;

    public LoginView() {
        // Impostazioni di layout e margini
        setupLayout();

        // Creazione dei componenti
        createUserField();
        createPasswordField();
        createLoginButton();
        createSigninLabel();
        usernameLabel = createLabel("Username or Email: ");
        passwordLabel = createLabel("Password: ");

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

    private CustomLabel createLabel(String text){
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(labelBuilder);
        labelBuilder.text(text).size(LABEL_SIZE);
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    private void createUserField() {
        // Creazione del campo di testo per lo username/Email
        UIBuilder usernameFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(usernameFieldBuilder);
        usernameFieldBuilder.size(FIELD_SIZE)
                .placeholder("Username or Email");

        // Usa la factory per creare il campo di testo
        UIComponentFactory usernameFieldFactory = new CustomTextFieldFactory(usernameFieldBuilder);
        userField = (CustomTextField) usernameFieldFactory.orderComponent(usernameFieldBuilder);
    }

    private void createPasswordField() {
        // Creazione del campo di testo per la password
        UIBuilder passwordFieldBuilder = new CustomPasswordFieldBuilder();
        UIDirector.buildStandardPasswordField(passwordFieldBuilder);
        passwordFieldBuilder.text("Password")
                .size(FIELD_SIZE)
                .placeholder("Password");

        // Usa la factory per creare il campo di testo
        UIComponentFactory passwordFieldFactory = new CustomTextFieldFactory(passwordFieldBuilder);
        passwordField = (CustomPasswordField) passwordFieldFactory.orderComponent(passwordFieldBuilder);
    }

    private void createLoginButton() {
        // Creazione del pulsante di login
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Login")
                .size(BUTTON_SIZE)
                .action(new LoginCommand(this));

        // Usa la factory per creare il pulsante
        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        loginButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);
    }

    private void createSigninLabel() {
        // Creazione del link per il signup
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text("Don't have an account? Sign up!")
                .size(BUTTON_SIZE)
                .action(new GoToSigninCommand());

        // Usa la factory per creare la label
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        signinLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);
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
