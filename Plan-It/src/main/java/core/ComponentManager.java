// Package: core

package core;

import view.ApplicationWindow;
import view.panel.TaskView;
import view.panel.createPanel.CreatePanel;
import view.panel.LoginView;
import view.panel.DeskView;
import view.panel.SigninView;

import javax.swing.*;

public class ComponentManager {
    private static ComponentManager instance;
    private String user,path;

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
    public DeskView getDeskView() {
        return new DeskView(user,path);
    }
    public TaskView getTaskView(String taskTitle){
        return new TaskView(taskTitle,user,path);
    }
    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {
        ApplicationWindow.getInstance().setPanel(panel);
    }

    public void setUserAndPath(String user, String path){this.user=user; this.path=path;}
    public String getUser() {return user;}
    public String getPath() {return path;}
}
