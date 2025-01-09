package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToModifyTaskCommand implements ActionCommand {
    private final String taskTitle;

    public GoToModifyTaskCommand(String taskTitle) {
        this.taskTitle=taskTitle;
    }

    @Override
    public void execute(){
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getModifyTask(taskTitle));
    }
}
