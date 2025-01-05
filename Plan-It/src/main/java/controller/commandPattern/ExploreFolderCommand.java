package controller.commandPattern;

import core.ComponentManager;

public class ExploreFolderCommand implements ActionCommand {
    private final String userInput, folderName;

    public ExploreFolderCommand(String userInput, String folderName) {
        this.userInput = userInput;
        this.folderName = folderName;
    }

    @Override public void execute() {
        ComponentManager.getInstance().setPath(userInput, folderName);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
    }
}
