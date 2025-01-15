package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToTaskViewCommand implements ActionCommand {
    private final String taskTitle, user;
    private final int startFolderId;

    public GoToTaskViewCommand(String taskTitle, String user, int startFolderId){
        this.taskTitle = taskTitle;
        this.user = user;
        this.startFolderId=startFolderId;
    }

    @Override public void execute() {
        ComponentManager.getInstance().setPath(user,startFolderId);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getTaskView(taskTitle));
    }
}
