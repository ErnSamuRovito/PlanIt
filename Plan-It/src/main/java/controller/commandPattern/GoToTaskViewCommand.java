package controller.commandPattern;

import core.ComponentManager;

public class GoToTaskViewCommand implements ActionCommand{
    private final String taskTitle;

    public GoToTaskViewCommand(String taskTitle){this.taskTitle = taskTitle;}

    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getTaskView(taskTitle));
    }
}
