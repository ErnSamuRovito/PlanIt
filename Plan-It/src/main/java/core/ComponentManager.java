package core;

import view.ApplicationWindow;
import view.panel.TaskView;
import view.panel.panelDecorators.CreatePanel;
import view.panel.LoginView;
import view.panel.DeskView;
import view.panel.SigninView;
import view.panel.panelDecorators.FolderModifyDecorator;
import view.panel.panelDecorators.TaskModifyDecorator;

import javax.swing.*;

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

    public TaskModifyDecorator getModifyTask(String taskTitle) {
        System.out.println("GET_MODIFY_TASK CALLED: " + taskTitle);
        return new TaskModifyDecorator(new CreatePanel(), taskTitle, user, currFolder);
    }
    public FolderModifyDecorator getModifyFolder(String folderTitle) {
        return new FolderModifyDecorator(new CreatePanel(), user, folderTitle);
    }


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
