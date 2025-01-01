// Package: core

package core;

import model.dao.task.TaskDAOImpl;
import view.ApplicationWindow;
import view.panel.TaskView;
import view.panel.createPanel.CreatePanel;
import view.panel.LoginView;
import view.panel.DeskView;
import view.panel.SigninView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ComponentManager {
    private static ComponentManager instance;
    private String user, currFolder;

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
        return new DeskView(user, currFolder);
    }
    public TaskView getTaskView(String taskTitle){
        return new TaskView(taskTitle,user,currFolder);
    }
    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {ApplicationWindow.getInstance().setPanel(panel);}

    public void setPath(String user, String currFolder){this.user=user; this.currFolder=currFolder;}
    public String getUser() {return user;}
    public String getCurrFolder() {return currFolder;}
}
