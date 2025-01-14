package view.panel;

import controller.commandPattern.ModifyFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.controllers.FolderController;
import model.services.FolderService;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class FolderModifyView extends TemplateView {
    private final FolderService folderService;
    private final FolderController folderController;
    private final String user, folder;
    private final int id;

    public FolderModifyView(String user, String folder) {
        this.user = user;
        this.folder = folder;

        folderService = new FolderService();
        folderController = new FolderController(folderService);
        id=folderController.getFolderIdByNameAndOwner(folder,user);

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildFolderNameL = registry.getFactory("Label").createBuild();
        buildFolderNameL.text("Folder name");
        UIBuilder buildFolderNameTF = registry.getFactory("TextField").createBuild();
        buildFolderNameTF.text(folder);

        UIBuilder buildModifyFolderB = registry.getFactory("Button").createBuild();
        buildModifyFolderB
                .text("Modify")
                .action(new ModifyFolderCommand(this));

        UIBuilder buildBackLabel = registry.getFactory("ClickableLabel").createBuild();
        buildBackLabel
                .text("Back")
                .action(new GoToDeskViewCommand());

        builders.add(buildFolderNameL);     //0
        builders.add(buildFolderNameTF);    //1
        builders.add(buildModifyFolderB);   //2
        builders.add(buildBackLabel);       //3
    }

    @Override
    protected void addComponentsToPanel() {
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corretta
            add((Component) components.get(i), gbc); // Usa gbc
        }
    }

    public String getFolderName(){return ((CustomTextField) components.get(1)).getText();}
    public int getFolderId(){return id;}
}
