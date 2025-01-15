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

        builders.add(createLabelBuilder(registry, "Username"));            // 0: Username label
        builders.add(createTextFieldBuilder(registry, "Username"));       // 1: Username input
        builders.add(createLabelBuilder(registry, "Email"));              // 2: Email label
        builders.add(createTextFieldBuilder(registry, "Email"));          // 3: Email input
        builders.add(createLabelBuilder(registry, "Password"));           // 4: Password label
        builders.add(createPasswordFieldBuilder(registry, "Password"));   // 5: Password input
        builders.add(createLabelBuilder(registry, "Confirm password"));   // 6: Confirm password label
        builders.add(createPasswordFieldBuilder(registry, "Confirm password")); // 7: Confirm password input
        builders.add(createSigninButtonBuilder(registry));                 // 8: Sign in button
        builders.add(createClickableLabelBuilder(registry));               // 9: Login link
    }

    private UIBuilder createLabelBuilder(UIComponentFactoryRegistry registry, String text) {
        return registry.getFactory("Label").createBuild().text(text);
    }

    private UIBuilder createTextFieldBuilder(UIComponentFactoryRegistry registry, String placeholder) {
        return registry.getFactory("TextField").createBuild().placeholder(placeholder);
    }

    private UIBuilder createPasswordFieldBuilder(UIComponentFactoryRegistry registry, String placeholder) {
        return registry.getFactory("PasswordField").createBuild().placeholder(placeholder);
    }

    private UIBuilder createSigninButtonBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("Button").createBuild()
                .text("Sign in")
                .action(new SigninCommand(this));
    }

    private UIBuilder createClickableLabelBuilder(UIComponentFactoryRegistry registry) {
        return registry.getFactory("ClickableLabel").createBuild()
                .text("Already have an account? Log in!")
                .action(new GoToLoginCommand());
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Position the component in the correct row
            add((Component) components.get(i), gbc); // Use gbc to add component
        }
    }

    public String getUsername() {
        return ((CustomTextField) components.get(1)).getText();
    }

    public String getEmail() {
        return ((CustomTextField) components.get(3)).getText();
    }

    public String getPassword() {
        return ((CustomPasswordField) components.get(5)).getPasswordString();
    }

    public String getConfirmPassword() {
        return ((CustomPasswordField) components.get(7)).getPasswordString();
    }
}
