package model;

import model.persistance.SqLiteConnection;
import model.persistance.dao.user.UserDAOImpl;
import model.persistance.dao.user.UserDB;

import java.sql.Connection;
import java.sql.SQLException;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;

    // Private static instance variable
    private static User instance;

    // Public static method to get the instance of User
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password.toCharArray();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to load user data (Note: this can be adjusted based on how you want to handle Singleton data)
    public void loadUser(int id) {
        UserDB user;
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            user= userDAO.getUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        this.id=user.getId();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.email=user.getEmail();
    }

    // Optional: toString() method for debugging
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
