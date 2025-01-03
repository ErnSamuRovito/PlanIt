package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.user.UserDAO;
import model.dao.user.UserDAOImpl;
import view.panel.LoginView;

public class DeleteUserCommand implements ActionCommand{
    int userId;

    public DeleteUserCommand(int userId) {
        this.userId = userId;
    }

    @Override
    public void execute() {
        UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
        userDAO.deleteUser(userId);
        ComponentManager.getInstance().setPanel(new LoginView());
    }
}
