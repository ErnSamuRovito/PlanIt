package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToDeskViewCommand implements ActionCommand {
    private final String user,folder;
    public GoToDeskViewCommand(){
        this.user=ComponentManager.getInstance().getUser();
        this.folder=ComponentManager.getInstance().getCurrFolder();
    }
    public GoToDeskViewCommand(String user, String folder) {
        this.user = user;
        this.folder = folder;
    }

    @Override public void execute() {
        ComponentManager.getInstance().setPath(user,folder);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
    }
}
