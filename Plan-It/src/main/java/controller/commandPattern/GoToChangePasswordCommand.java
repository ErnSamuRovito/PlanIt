package controller.commandPattern;

import core.ComponentManager;
import view.panel.ChangePasswordView;

public class GoToChangePasswordCommand implements ActionCommand{
    @Override
    public void execute() {
        ComponentManager.getInstance().setPanel(new ChangePasswordView());
    }
}
