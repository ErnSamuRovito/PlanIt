package controller.commandPattern;

import core.ComponentManager;

public class GoToTaskViewCommand implements ActionCommand{
    private final String taskTitle, startFolder;

    public GoToTaskViewCommand(String taskTitle, String startFolder){
        this.taskTitle = taskTitle;
        this.startFolder=startFolder;
    }

    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getTaskView(taskTitle,startFolder));
    }
}
