package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;

public class GoToSigninCommand implements ActionCommand {
    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getSigninView());
    }
}
