package core;

import model.persistance.SqLiteConnection;
import model.persistance.dao.folder.FolderDAOImpl;
import view.ApplicationWindow;
import view.panels.*;
import view.panels.TaskModifyView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class ComponentManager {
    private static ComponentManager instance;
    private String user, currFolderName;
    private Integer currFolderId;
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
        return new TaskModifyView(taskTitle, user, currFolderName);
    }
    public FolderModifyView getModifyFolder(String folderTitle) {
        return new FolderModifyView(user, folderTitle);
    }


    public DeskView getDeskView() {
        return new DeskView(user, currFolderName);
    }
    public TaskView getTaskView(String taskTitle){
        return new TaskView(taskTitle,user, currFolderName);
    }

    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {ApplicationWindow.getInstance().setPanel(panel);}

    public void setPath(String user, Integer currFolderId){
        this.user=user;
        this.currFolderId=currFolderId;
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            this.currFolderName=folderDAO.getFolderById(currFolderId).getFolderName();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String getUser() {return user;}
    public String getCurrFolderName() {return currFolderName;}

    public void setCuttedComponent(String cuttedComponentType, Integer cuttedComponentId){
        this.cuttedComponentType = cuttedComponentType;
        this.cuttedComponentId = cuttedComponentId;
    }
    public String getCuttedComponentType() {return cuttedComponentType;}
    public Integer getCuttedComponentId() {return cuttedComponentId;}
}
