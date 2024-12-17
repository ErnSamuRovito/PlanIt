package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
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

public class LoginCommand implements ActionCommand{
    private final LoginView parentView;

    public LoginCommand(LoginView parentView) {
        this.parentView = parentView;
    }

    @Override public void execute() {
        if (parentView != null) {
            CustomTextField usernameField = parentView.getUserField();
            String userInput = usernameField.getText();

            CustomPasswordField passwordField = parentView.getPasswordField();
            String passwordInput = passwordField.getPasswordString();

            int idLogged;
            try (Connection connection = SqLiteConnection.getInstance().getConnection()){
                UserDAOImpl userDAO = new UserDAOImpl(connection);
                idLogged=userDAO.logUser(userInput, passwordInput);
            } catch (SQLException e) {throw new RuntimeException(e);}

            if (idLogged != -1) {
                User.getInstance().loadUser(idLogged);
                AvatarPlant.getInstance().loadPlant(User.getInstance().getId());
                AvatarPlant.getInstance().updateState();
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView(userInput,"/root"));
            } else {
                showMessageDialog(null, "Login failed. Try again.", "Plan-It", ERROR_MESSAGE);
            }
        }
    }
}
