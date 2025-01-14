package core;

import controller.controllers.TaskController;
import model.services.TaskService;
import view.ApplicationWindow;
import view.panel.*;
import view.panel.TaskModifyView;

import javax.swing.*;

public class ComponentManager {
    private static ComponentManager instance;
    private String user, currFolder;
    private String cuttedComponentType;
    private Integer cuttedComponentId;

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

    public TaskModifyView getModifyTask(String taskTitle) {
        return new TaskModifyView(taskTitle, user, currFolder);
    }
    public FolderModifyView getModifyFolder(String folderTitle) {
        return new FolderModifyView(user, folderTitle);
    }


    public DeskView getDeskView() {
        return new DeskView(user, currFolder);
    }
    public TaskView getTaskView(String taskTitle){
        TaskService taskService = new TaskService();
        TaskController taskController = new TaskController(taskService);
        return new TaskView(taskTitle,user,currFolder,taskController);
    }

    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {ApplicationWindow.getInstance().setPanel(panel);}

    public void setPath(String user, String currFolder){this.user=user; this.currFolder=currFolder;}
    public String getUser() {return user;}
    public String getCurrFolder() {return currFolder;}

    public void setCuttedComponent(String cuttedComponentType, Integer cuttedComponentId){
        this.cuttedComponentType = cuttedComponentType;
        this.cuttedComponentId = cuttedComponentId;
    }
    public String getCuttedComponentType() {return cuttedComponentType;}
    public Integer getCuttedComponentId() {return cuttedComponentId;}
}
