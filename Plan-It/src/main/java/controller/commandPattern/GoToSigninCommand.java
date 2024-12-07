package controller.commandPattern;

import view.ApplicationWindow;
import view.panel.SigninView;

public class GoToSigninCommand implements ActionCommand{
    @Override public void execute() {
        ApplicationWindow.getInstance().setPanel(new SigninView());
    }
}
