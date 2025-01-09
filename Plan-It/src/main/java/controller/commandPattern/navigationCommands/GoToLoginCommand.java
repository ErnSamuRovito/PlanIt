package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToLoginCommand implements ActionCommand {
    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
    }
}