package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import view.panels.ChangePasswordView;

public class GoToChangePasswordCommand implements ActionCommand {
    @Override
    public void execute() {
        ComponentManager.getInstance().setPanel(new ChangePasswordView());
    }
}
