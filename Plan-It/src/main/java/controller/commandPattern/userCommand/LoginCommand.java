package controller.commandPattern.userCommand;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import core.SqLiteConnection;
import model.PasswordUtils;
import model.User;
import model.dao.user.UserDAOImpl;
import model.plant.AvatarPlant;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.LoginView;

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
        if (parentView != null) {
            CustomTextField usernameField = parentView.getUserField();
            String userInput = usernameField.getText();
            int idLogged = -1;

            CustomPasswordField passwordField = parentView.getPasswordField();
            String passwordInput = passwordField.getPasswordString();

            try (Connection connection = SqLiteConnection.getInstance().getConnection()){
                UserDAOImpl userDAO = new UserDAOImpl(connection);

                // verify hashed password
                idLogged = userDAO.logUser(userInput, passwordInput);


            } catch (SQLException e) {throw new RuntimeException(e);}

            if (idLogged != -1) {
                User.getInstance().loadUser(idLogged);
                AvatarPlant.getInstance().loadPlant(User.getInstance().getId());
                AvatarPlant.getInstance().updateState();
                ComponentManager.getInstance().setPath(userInput,"/root");
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            } else {
                showMessageDialog(null, "Login failed. Try again.", "Plan-It", ERROR_MESSAGE);
            }
        }
    }
}
