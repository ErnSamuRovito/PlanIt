package controller.commandPattern;

import core.ComponentManager;
import view.panel.SettingsView;

public class GoToSettingsCommand implements ActionCommand{
    @Override
    public void execute() {
        ComponentManager.getInstance().setPanel(new SettingsView());
    }
}
