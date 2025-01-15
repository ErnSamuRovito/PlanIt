package controller.commandPattern.navigationCommands;

import controller.commandPattern.ActionCommand;
import controller.controllers.FolderController;
import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.services.FolderService;

import java.sql.Connection;
import java.sql.SQLException;

public class GoToDeskViewCommand implements ActionCommand {
    private final String user,folder;
    public GoToDeskViewCommand(){
        this.user=ComponentManager.getInstance().getUser();
        this.folder=ComponentManager.getInstance().getCurrFolderName();
    }
    public GoToDeskViewCommand(String user, String folder) {
        this.user = user;
        this.folder = folder;
    }

    @Override public void execute() {
        FolderController folderController=new FolderController(new FolderService());
        int folderId=folderController.getFolderIdByNameAndOwner(folder,ComponentManager.getInstance().getUser());
        ComponentManager.getInstance().setPath(user,folderId);
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
    }
}
