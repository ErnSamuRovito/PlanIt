package model;

import core.SqLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public String getPassword() {
        return password;
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
        String query = "SELECT * FROM User WHERE id = ?";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected);
            System.out.println("Query executed successfully!");
        } catch (SQLException e) {
            System.err.println("Error loading user: " + e.getMessage());
        }
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
