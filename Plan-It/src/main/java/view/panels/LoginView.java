package view.panels;

import controller.commandPattern.navigationCommands.GoToSigninCommand;
import controller.commandPattern.userCommand.LoginCommand;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class LoginView extends TemplateView {

    // Costanti per le posizioni dei componenti nella lista
    private static final int USERNAME_FIELD_INDEX = 1;
    private static final int PASSWORD_FIELD_INDEX = 3;

    public LoginView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        // Creazione dei componenti
        UIBuilder buildLabelUsername = registry.getFactory("Label").createBuild();
        buildLabelUsername.text("Username");

        UIBuilder buildTextField = registry.getFactory("TextField").createBuild();
        buildTextField.placeholder("Username");

        UIBuilder buildLabelPassword = registry.getFactory("Label").createBuild();
        buildLabelPassword.text("Password");

        UIBuilder buildPassword = registry.getFactory("PasswordField").createBuild();
        buildPassword.placeholder("Password");

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("Login")
                .action(new LoginCommand(this));

        UIBuilder buildClickableLabel = registry.getFactory("ClickableLabel").createBuild();
        buildClickableLabel
                .text("Don't have an account? Sign up!")
                .action(new GoToSigninCommand());

        // Ordine di inserimento è cruciale
        builders.add(buildLabelUsername);
        builders.add(buildTextField);
        builders.add(buildLabelPassword);
        builders.add(buildPassword);
        builders.add(buildButton);
        builders.add(buildClickableLabel);
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corrispondente
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }

    public String getUsername() {
        // Ritorna il valore dal campo Username
        return ((CustomTextField) components.get(USERNAME_FIELD_INDEX)).getText();
    }

    public String getPassword() {
        // Ritorna il valore dal campo Password
        return ((CustomPasswordField) components.get(PASSWORD_FIELD_INDEX)).getPasswordString();
    }
}
