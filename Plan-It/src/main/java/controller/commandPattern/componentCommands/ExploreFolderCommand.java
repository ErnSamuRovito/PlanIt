package controller.commandPattern.componentCommands;

import controller.commandPattern.ActionCommand;
import controller.controllers.FolderController;
import core.ComponentManager;
import model.services.FolderService;

public class ExploreFolderCommand implements ActionCommand {
    private final String userInput, folderName;

    public ExploreFolderCommand(String userInput, String folderName) {
        this.userInput = userInput;
        this.folderName = folderName;
    }

    @Override public void execute() {
        FolderController folderController=new FolderController(new FolderService());
        int folderId=folderController.getFolderIdByNameAndOwner(folderName,ComponentManager.getInstance().getUser());

        ComponentManager.getInstance().setPath(userInput, folderId);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
    }
}
