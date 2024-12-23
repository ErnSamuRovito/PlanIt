package controller.commandPattern;

import core.ComponentManager;

public class ExploreFolderCommand implements ActionCommand {
    private final String userInput, folderName;

    public ExploreFolderCommand(String userInput, String folderName) {
        this.userInput = userInput;
        this.folderName = folderName;
    }

    @Override public void execute() {
        System.out.println("Explore folder: " + folderName + " ("+userInput+")");
        ComponentManager.getInstance().setUserAndPath(userInput, folderName);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
    }
}
