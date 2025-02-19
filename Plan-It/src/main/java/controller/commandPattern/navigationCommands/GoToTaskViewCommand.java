package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToTaskViewCommand implements ActionCommand {
    private final String taskTitle, user, startFolder;

    public GoToTaskViewCommand(String taskTitle, String user, String startFolder){
        this.taskTitle = taskTitle;
        this.user = user;
        this.startFolder=startFolder;
    }

    @Override public void execute() {
        ComponentManager.getInstance().setPath(user,startFolder);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getTaskView(taskTitle));
    }
}
