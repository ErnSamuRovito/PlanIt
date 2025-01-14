package view.panel;

import controller.commandPattern.userCommand.ChangePasswordCommand;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomPasswordField;

import java.awt.*;

public class ChangePasswordView extends TemplateView {
    public ChangePasswordView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildOldPasswordL = registry.getFactory("Label").createBuild();
        buildOldPasswordL.text("Current Password");
        UIBuilder buildOldPasswordPF = registry.getFactory("PasswordField").createBuild();
        buildOldPasswordPF.placeholder("Password");

        UIBuilder buildPasswordL = registry.getFactory("Label").createBuild();
        buildPasswordL.text("New Password");
        UIBuilder buildPasswordPF = registry.getFactory("PasswordField").createBuild();
        buildPasswordPF.placeholder("Password");

        UIBuilder buildConfirmPasswordL = registry.getFactory("Label").createBuild();
        buildConfirmPasswordL.text("Confirm password");
        UIBuilder buildConfirmPasswordPF = registry.getFactory("PasswordField").createBuild();
        buildConfirmPasswordPF.placeholder("Confirm password");

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("Change password")
                .action(new ChangePasswordCommand(this));

        builders.add(buildOldPasswordL);            //0
        builders.add(buildOldPasswordPF);           //1
        builders.add(buildPasswordL);               //2
        builders.add(buildPasswordPF);              //3
        builders.add(buildConfirmPasswordL);        //4
        builders.add(buildConfirmPasswordPF);       //6
        builders.add(buildButton);                  //7
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corretta
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }

    public char[] getCurrPassword(){
        String passwordString = ((CustomPasswordField) components.get(1)).getPasswordString();
        return passwordString.toCharArray();  // Converte la stringa in un char[]
    }
    public char[] getNewPassword(){
        String passwordString = ((CustomPasswordField) components.get(3)).getPasswordString();
        return passwordString.toCharArray();  // Converte la stringa in un char[]
    }
    public char[] getConfirmPassword(){
        String passwordString = ((CustomPasswordField) components.get(5)).getPasswordString();
        return passwordString.toCharArray();  // Converte la stringa in un char[]
    }
}
