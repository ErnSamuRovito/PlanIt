package view.views;

import model.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIBuilders.UIBuilder;

import java.awt.*;

public class LoginView extends TemplateView {
    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildTextField = registry.getFactory("TextField").createBuild();
            buildTextField.placeholder("username");

        UIBuilder buildPassword = registry.getFactory("PasswordField").createBuild();
            buildPassword.placeholder("password");

        builders.add(buildTextField);
        builders.add(buildPassword);
        builders.add(registry.getFactory("Button").createBuild());
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corretta
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }
}
