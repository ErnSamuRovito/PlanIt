package controller.commandPattern.userCommand;

import controller.commandPattern.ActionCommand;
import controller.controllers.FolderController;
import core.ComponentManager;
import model.persistance.SqLiteConnection;
import model.User;
import model.persistance.dao.user.UserDAOImpl;
import model.plantStates.AvatarPlant;
import model.services.FolderService;
import view.panels.LoginView;

import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class LoginCommand implements ActionCommand {
    private final LoginView parentView;

    public LoginCommand(LoginView parentView) {
        this.parentView = parentView;
    }

    @Override public void execute() {
        //resetto il componente "tagliato".
        ComponentManager.getInstance().setCuttedComponent(null,null);

        if (parentView != null) {
            String userInput = parentView.getUsername();
            String passwordInput = parentView.getPassword();
            int idLogged = -1;

            try (Connection connection = SqLiteConnection.getInstance().getConnection()){
                UserDAOImpl userDAO = new UserDAOImpl(connection);

                // verify hashed password
                idLogged = userDAO.logUser(userInput, passwordInput);


            } catch (SQLException e) {throw new RuntimeException(e);}

            if (idLogged != -1) {
                User.getInstance().loadUser(idLogged);
                AvatarPlant.getInstance().loadPlant(User.getInstance().getId());
                AvatarPlant.getInstance().updateState();

                FolderController folderController=new FolderController(new FolderService());
                int folderId=folderController.getFolderIdByNameAndOwner("/root",userInput);

                ComponentManager.getInstance().setPath(userInput,folderId);
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            } else {
                showMessageDialog(null, "Login failed. Try again.", "Plan-It", ERROR_MESSAGE);
            }
        }
    }
}
