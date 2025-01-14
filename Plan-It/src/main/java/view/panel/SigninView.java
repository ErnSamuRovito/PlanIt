package view.panel;

import controller.commandPattern.navigationCommands.GoToLoginCommand;
import controller.commandPattern.userCommand.SigninCommand;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class SigninView extends TemplateView {
    public SigninView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildUsernameL = registry.getFactory("Label").createBuild();
        buildUsernameL.text("Username");

        UIBuilder buildUsernameTF = registry.getFactory("TextField").createBuild();
        buildUsernameTF.placeholder("Username");

        UIBuilder buildEmailL = registry.getFactory("Label").createBuild();
        buildEmailL.text("Email");

        UIBuilder buildEmailTF = registry.getFactory("TextField").createBuild();
        buildEmailTF.placeholder("Email");

        UIBuilder buildPasswordL = registry.getFactory("Label").createBuild();
        buildPasswordL.text("Password");

        UIBuilder buildPasswordPF = registry.getFactory("PasswordField").createBuild();
        buildPasswordPF.placeholder("Password");

        UIBuilder buildConfirmPasswordL = registry.getFactory("Label").createBuild();
        buildConfirmPasswordL.text("Confirm password");

        UIBuilder buildConfirmPasswordPF = registry.getFactory("PasswordField").createBuild();
        buildConfirmPasswordPF.placeholder("Confirm password");

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("Sign in")
                .action(new SigninCommand(this));

        UIBuilder buildClickableLabel = registry.getFactory("ClickableLabel").createBuild();
        buildClickableLabel
                .text("Already have an account? Log in!")
                .action(new GoToLoginCommand());

        //IMPORTANTISSIMO RISPETTARE L'ORDINE DI INSERIMENTO!
        builders.add(buildUsernameL);               //0
        builders.add(buildUsernameTF);              //1
        builders.add(buildEmailL);                  //2
        builders.add(buildEmailTF);                 //3
        builders.add(buildPasswordL);               //4
        builders.add(buildPasswordPF);              //5
        builders.add(buildConfirmPasswordL);        //6
        builders.add(buildConfirmPasswordPF);       //7
        builders.add(buildButton);                  //8
        builders.add(buildClickableLabel);          //9
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corretta
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }

    public String getUsername(){return ((CustomTextField) components.get(1)).getText();}
    public String getEmail(){return ((CustomTextField) components.get(3)).getText();}
    public String getPassword(){return ((CustomPasswordField) components.get(5)).getPasswordString();}
    public String getConfirmPassword(){return ((CustomPasswordField) components.get(7)).getPasswordString();}
}
