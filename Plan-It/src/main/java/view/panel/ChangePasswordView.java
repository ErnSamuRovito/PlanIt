package view.panel;

import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
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

        // Creazione dei componenti UI per la vista
        UIBuilder buildOldPasswordL = createLabel("Current Password");
        UIBuilder buildOldPasswordPF = createPasswordField("Password");

        UIBuilder buildPasswordL = createLabel("New Password");
        UIBuilder buildPasswordPF = createPasswordField("Password");

        UIBuilder buildConfirmPasswordL = createLabel("Confirm password");
        UIBuilder buildConfirmPasswordPF = createPasswordField("Confirm password");

        UIBuilder buildButton = registry.getFactory("Button").createBuild();
        buildButton
                .text("Change password")
                .action(new ChangePasswordCommand(this));

        UIBuilder buildBackL = registry.getFactory("ClickableLabel").createBuild();
        buildBackL.text("Back").action(new GoToDeskViewCommand());

        // Aggiunta dei componenti nella lista builder
        builders.add(buildOldPasswordL);
        builders.add(buildOldPasswordPF);
        builders.add(buildPasswordL);
        builders.add(buildPasswordPF);
        builders.add(buildConfirmPasswordL);
        builders.add(buildConfirmPasswordPF);
        builders.add(buildButton);
        builders.add(buildBackL);
    }

    private UIBuilder createLabel(String text) {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();
        UIBuilder builder = registry.getFactory("Label").createBuild();
        builder.text(text);
        return builder;
    }

    private UIBuilder createPasswordField(String placeholder) {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();
        UIBuilder builder = registry.getFactory("PasswordField").createBuild();
        builder.placeholder(placeholder);
        return builder;
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corretta
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }

    // Metodo generale per ottenere la password
    private char[] getPassword(int index) {
        String passwordString = ((CustomPasswordField) components.get(index)).getPasswordString();
        return passwordString.toCharArray();  // Converte la stringa in un char[]
    }

    // Metodi specifici per ottenere le password dai campi
    public char[] getCurrPassword() {
        return getPassword(1);  // Campo della password corrente
    }

    public char[] getNewPassword() {
        return getPassword(3);  // Campo della nuova password
    }

    public char[] getConfirmPassword() {
        return getPassword(5);  // Campo della conferma password
    }
}
