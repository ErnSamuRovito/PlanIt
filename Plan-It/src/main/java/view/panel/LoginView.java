package view.panel;

import controller.commandPattern.navigationCommands.GoToSigninCommand;
import controller.commandPattern.userCommand.LoginCommand;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class LoginView extends TemplateView {
    public LoginView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

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

        //IMPORTANTISSIMO RISPETTARE L'ORDINE DI INSERIMENTO!
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
            gbc.gridy = i; // Posiziona il componente nella riga corretta
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }

    public String getUsername(){
        //IL COMPONENTE IN POSIZIONE 1 *DEVE* ESSERE IL TEXT FIELD DELL'USERNAME!
        return ((CustomTextField) components.get(1)).getText();
    }
    public String getPassword(){
        //IL COMPONENTE IN POSIZIONE 1 *DEVE* ESSERE IL PASSWORD FIELD DELLA PASSWORD!
        return ((CustomPasswordField) components.get(3)).getPasswordString();
    }
}
