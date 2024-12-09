package controller.commandPattern;

import core.ComponentManager;

public class GoToLoginCommand implements ActionCommand{
    @Override public void execute() {
        //ApplicationWindow.getInstance().setPanel(new LoginView());
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
    }
}