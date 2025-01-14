package view.panel;

import controller.commandPattern.navigationCommands.GoToChangePasswordCommand;
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

    public SettingsView(){
        UserService userService = new UserService();
        UserController userController = new UserController(userService);
        userDB=userController.getUserByUsername();

        AvatarPlantService avatarPlantService = new AvatarPlantService();
        AvatarPlantController avatarPlantController = new AvatarPlantController(avatarPlantService);
        avatarPlantDB=avatarPlantController.getPlantByOwnerId(userDB.getId());

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildUsernameL = registry.getFactory("Label").createBuild();
        buildUsernameL.text("Username");
        UIBuilder buildUsernameTF = registry.getFactory("TextField").createBuild();
        buildUsernameTF.placeholder(userDB.getUsername());

        UIBuilder buildEmailL = registry.getFactory("Label").createBuild();
        buildEmailL.text("Email");
        UIBuilder buildEmailTF = registry.getFactory("TextField").createBuild();
        buildEmailTF.placeholder(userDB.getEmail());

        UIBuilder buildPlantNameL = registry.getFactory("Label").createBuild();
        buildPlantNameL.text("Plant name");
        UIBuilder buildPlantNameTF = registry.getFactory("TextField").createBuild();
        buildPlantNameTF.placeholder(avatarPlantDB.getName());

        UIBuilder buildChangePasswordB = registry.getFactory("Button").createBuild();
        buildChangePasswordB
                .text("Change password")
                .action(new GoToChangePasswordCommand());

        UIBuilder buildDeleateUserB = registry.getFactory("Button").createBuild();
        buildDeleateUserB
                .text("Delete User")
                .backgroundColor(GlobalResources.COLOR_RED1)
                .hoverBackgroundColor(GlobalResources.COLOR_RED2)
                .pressedBackgroundColor(GlobalResources.COLOR_RED1)
                .action(new DeleteUserCommand(userDB.getId()));

        UIBuilder buildSaveB = registry.getFactory("Button").createBuild();
        buildSaveB
                .text("Save")
                .action(new SaveSettingsCommand(this));

        builders.add(buildUsernameL);               //0
        builders.add(buildUsernameTF);              //1
        builders.add(buildEmailL);                  //2
        builders.add(buildEmailTF);                 //3
        builders.add(buildPlantNameL);              //4
        builders.add(buildPlantNameTF);             //5
        builders.add(buildChangePasswordB);         //6
        builders.add(buildDeleateUserB);            //7
        builders.add(buildSaveB);                   //8
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
    public String getPlantName(){return ((CustomTextField) components.get(5)).getText();}
}
