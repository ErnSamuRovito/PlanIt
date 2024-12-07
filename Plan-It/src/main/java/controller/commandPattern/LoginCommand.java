package controller.commandPattern;

import view.ApplicationWindow;
import view.panel.DeskView;

public class LoginCommand implements ActionCommand{
    @Override public void execute() {
        ApplicationWindow.getInstance().setPanel(new DeskView());
    }
}
