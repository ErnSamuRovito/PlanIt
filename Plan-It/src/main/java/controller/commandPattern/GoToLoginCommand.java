package controller.commandPattern;

import view.ApplicationWindow;
import view.panel.LoginView;

public class GoToLoginCommand implements ActionCommand{
    @Override public void execute() {
        ApplicationWindow.getInstance().setPanel(new LoginView());
    }
}
