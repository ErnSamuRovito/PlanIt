package view.panel;

import controller.commandPattern.CreateFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class FolderCreateView extends TemplateView {

    public FolderCreateView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        UIBuilder buildFolderNameL = registry.getFactory("Label").createBuild();
        buildFolderNameL.text("Folder name");
        UIBuilder buildFolderNameTF = registry.getFactory("TextField").createBuild();
        buildFolderNameTF.placeholder("Folder name");

        UIBuilder buildModifyFolderB = registry.getFactory("Button").createBuild();
        buildModifyFolderB
                .text("Create")
                .action(new CreateFolderCommand(this));

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
}
