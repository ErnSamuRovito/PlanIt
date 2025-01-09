package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToModifyFolderCommand implements ActionCommand {
    private final String folderTitle;

    public GoToModifyFolderCommand(String folderTitle) {
        this.folderTitle=folderTitle;
    }

    @Override
    public void execute(){
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getModifyFolder(folderTitle));
    }
}
