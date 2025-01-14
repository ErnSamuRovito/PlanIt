package model.services;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.user.UserDAOImpl;
import model.dao.user.UserDB;

import java.sql.Connection;
import java.sql.SQLException;

public class UserService {
    public UserDB getUserByUsername() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            return userDAO.getUserByUsername(ComponentManager.getInstance().getUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
