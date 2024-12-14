// Package: core

package core;

import view.ApplicationWindow;
import view.panel.TaskView;
import view.panel.createPannel.CreatePanel;
import view.panel.LoginView;
import view.panel.DeskView;
import view.panel.SigninView;

import javax.swing.*;

public class ComponentManager {
    private static ComponentManager instance;

    // Costruttore privato per il singleton
    private ComponentManager() {}

    public static synchronized ComponentManager getInstance() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }

    public LoginView getLoginView() {return new LoginView();}
    public SigninView getSigninView() {return new SigninView();}
    public CreatePanel getCreatePanel() {return new CreatePanel();}
    public DeskView getDeskView(String user, String startFolder) {
        DataProvider dataProvider = new DatabaseDataProvider(user);
        dataProvider.setStartFolder(startFolder);
        return new DeskView(dataProvider);
    }
    public TaskView getTaskView(String taskName){
        return new TaskView();
    }

    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {
        ApplicationWindow.getInstance().setPanel(panel);
    }
}
