package view.panel;

import controller.commandPattern.GoToSigninCommand;
import controller.commandPattern.LoginCommand;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomClickableLabelFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField userField;
    private CustomPasswordField passwordField;
    private CustomButton loginButton;
    private CustomLabel signinLabel;

    public LoginView() {
        // Imposta layout e margini del pannello
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        UIDirector uiDirector = new UIDirector();

        // Creazione del campo di testo username usando il Builder e la Factory ----------------
        UIBuilder usernameFieldBuilder = new CustomTextFieldBuilder();
        uiDirector.buildStandardTextField(usernameFieldBuilder);
        usernameFieldBuilder.text("Username or Email").size(FIELD_SIZE).placeholder("Username or Email");
        // Usa la factory per creare il campo di testo username
        UIComponentFactory usernameFieldFactory = new CustomTextFieldFactory(usernameFieldBuilder);
        userField = (CustomTextField) usernameFieldFactory.orderComponent(usernameFieldBuilder);

        // Creazione del campo di testo password usando il Builder e la Factory ----------------
        UIBuilder passwordFieldBuilder = new CustomPasswordFieldBuilder();
        uiDirector.buildStandardPasswordField(passwordFieldBuilder);
        passwordFieldBuilder.text("Password").size(FIELD_SIZE).placeholder("Password");
        // Usa la factory per creare il campo di testo password
        UIComponentFactory passwordFieldFactory = new CustomTextFieldFactory(passwordFieldBuilder);
        passwordField = (CustomPasswordField) passwordFieldFactory.orderComponent(passwordFieldBuilder);

        // Creazione del pulsante di login usando il Builder e la Factory ----------------
        UIBuilder buttonBuilder =  new CustomButtonBuilder();
        uiDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Login").size(BUTTON_SIZE);
        buttonBuilder.action(new LoginCommand(this));
        // Usa la factory per creare il pulsante
        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        loginButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);
        //loginButton.addActionListener(new LoginButtonController());

        // Creazione del pulsante di login usando il Builder e la Factory ----------------
        UIBuilder labelBuilder =  new CustomLabelBuilder();
        uiDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text("Don't have an account? Sign up!").size(BUTTON_SIZE);
        labelBuilder.action(new GoToSigninCommand());
        // Usa la factory per creare il pulsante
        UIComponentFactory labelFactory = new CustomClickableLabelFactory(labelBuilder);
        signinLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);

        // Aggiungi componenti al pannello
        gbc.gridy = 0; add(userField, gbc);
        gbc.gridy = 1; add(passwordField, gbc);
        gbc.gridy = 2; add(loginButton, gbc);
        gbc.gridy = 3; add(signinLabel, gbc);
    }

    public CustomTextField getUserField(){return userField;}
    public CustomPasswordField getPasswordField() {return passwordField;}
}
