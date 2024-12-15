package controller.commandPattern;

import core.ComponentManager;

public class GoToDeskViewCommand implements ActionCommand{
    private final String user,folder;

    public GoToDeskViewCommand(String user, String folder) {
        this.user = user;
        this.folder = folder;
    }

    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView(user,folder));
    }
}
