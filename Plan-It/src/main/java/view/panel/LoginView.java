package view.panel;

import controller.LoginButtonController;
import controller.ToSigninController;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomClickableLabel;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomClickableLabelFactory;
import view.UICreationalPattern.UIFactories.CustomTextFieldFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JPanel {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField usernameField;
    private CustomPasswordField passwordField;
    private CustomButton loginButton;
    private CustomClickableLabel signinLabel;

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
        usernameFieldBuilder.text("Username").size(FIELD_SIZE).placeholder("Username");
        // Usa la factory per creare il campo di testo username
        UIComponentFactory usernameFieldFactory = new CustomTextFieldFactory(usernameFieldBuilder);
        usernameField = (CustomTextField) usernameFieldFactory.orderComponent(usernameFieldBuilder);

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
        // Usa la factory per creare il pulsante
        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        loginButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);
        loginButton.addActionListener(new LoginButtonController());

        // Creazione del pulsante di login usando il Builder e la Factory ----------------
        UIBuilder labelBuilder =  new CustomClickableLabelBuilder();
        uiDirector.buildStandardClickableLabel(labelBuilder);
        labelBuilder.text("Don't have an account? Sign up!").size(BUTTON_SIZE);
        // Usa la factory per creare il pulsante
        UIComponentFactory labelFactory = new CustomClickableLabelFactory(labelBuilder);
        signinLabel = (CustomClickableLabel) labelFactory.orderComponent(labelBuilder);
        signinLabel.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {new ToSigninController().actionPerformed(null);}
        });

        // Aggiungi componenti al pannello
        gbc.gridy = 0; add(usernameField, gbc);
        gbc.gridy = 1; add(passwordField, gbc);
        gbc.gridy = 2; add(loginButton, gbc);
        gbc.gridy = 3; add(signinLabel, gbc);
    }
}
