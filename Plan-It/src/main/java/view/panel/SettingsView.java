package view.panel;

import controller.commandPattern.ActionCommand;
import controller.commandPattern.navigationCommands.GoToChangePasswordCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.commandPattern.userCommand.DeleteUserCommand;
import controller.commandPattern.userCommand.SaveSettingsCommand;
import controller.controllers.AvatarPlantController;
import controller.controllers.UserController;
import core.GlobalResources;
import model.dao.avatarPlant.AvatarPlantDB;
import model.dao.user.UserDB;
import model.services.AvatarPlantService;
import model.services.UserService;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class SettingsView extends TemplateView {
    private UserDB userDB;
    private AvatarPlantDB avatarPlantDB;

    public SettingsView() {
        // Recupero le informazioni dell'utente e della pianta
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        userDB = userController.getUserByUsername();

        AvatarPlantService avatarPlantService = new AvatarPlantService();
        AvatarPlantController avatarPlantController = new AvatarPlantController(avatarPlantService);
        avatarPlantDB = avatarPlantController.getPlantByOwnerId(userDB.getId());

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        // Creazione dei componenti con metodi helper
        UIBuilder buildUsernameL = createLabel("Username");
        UIBuilder buildUsernameTF = createTextField(userDB.getUsername());

        UIBuilder buildEmailL = createLabel("Email");
        UIBuilder buildEmailTF = createTextField(userDB.getEmail());

        UIBuilder buildPlantNameL = createLabel("Plant name");
        UIBuilder buildPlantNameTF = createTextField(avatarPlantDB.getName());

        UIBuilder buildChangePasswordB = createButton("Change password", new GoToChangePasswordCommand());
        UIBuilder buildDeleateUserB = createButton("Delete User", new DeleteUserCommand(userDB.getId()), GlobalResources.COLOR_RED1, GlobalResources.COLOR_RED2);
        UIBuilder buildSaveB = createButton("Save", new SaveSettingsCommand(this));
        UIBuilder buildBackL = createClickableLabel(new GoToDeskViewCommand());

        // Aggiunta dei componenti
        builders.add(buildUsernameL);
        builders.add(buildUsernameTF);
        builders.add(buildEmailL);
        builders.add(buildEmailTF);
        builders.add(buildPlantNameL);
        builders.add(buildPlantNameTF);
        builders.add(buildChangePasswordB);
        builders.add(buildDeleateUserB);
        builders.add(buildSaveB);
        builders.add(buildBackL);
    }

    private UIBuilder createLabel(String text) {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();
        UIBuilder builder = registry.getFactory("Label").createBuild();
        builder.text(text);
        return builder;
    }

    private UIBuilder createTextField(String placeholder) {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();
        UIBuilder builder = registry.getFactory("TextField").createBuild();
        builder.placeholder(placeholder);
        return builder;
    }

    private UIBuilder createButton(String text, ActionCommand action) {
        return createButton(text, action, null, null);
    }

    private UIBuilder createButton(String text, ActionCommand action, Color bgColor, Color hoverColor) {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();
        UIBuilder builder = registry.getFactory("Button").createBuild();
        builder.text(text).action(action);
        if (bgColor != null && hoverColor != null) {
            builder.backgroundColor(bgColor)
                    .hoverBackgroundColor(hoverColor)
                    .pressedBackgroundColor(bgColor);
        }
        return builder;
    }

    private UIBuilder createClickableLabel(ActionCommand action) {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();
        UIBuilder builder = registry.getFactory("ClickableLabel").createBuild();
        builder.text("Back").action(action);
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

    public String getUsername() {
        return getTextFieldValue(1);
    }

    public String getEmail() {
        return getTextFieldValue(3);
    }

    public String getPlantName() {
        return getTextFieldValue(5);
    }

    private String getTextFieldValue(int index) {
        return ((CustomTextField) components.get(index)).getText();
    }
}
