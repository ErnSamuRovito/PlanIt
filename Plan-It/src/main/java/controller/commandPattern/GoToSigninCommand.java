package controller.commandPattern;

import core.ComponentManager;

public class GoToSigninCommand implements ActionCommand{
    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getSigninView());
    }
}
