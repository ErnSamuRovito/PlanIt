package controller.commandPattern;

import core.ComponentManager;

public class GoToLoginCommand implements ActionCommand{
    @Override public void execute() {
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
    }
}