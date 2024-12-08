package controller.commandPattern;

import core.SqLiteConnection;
import view.ApplicationWindow;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.DeskView;
import view.panel.LoginView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

            if (searchUser(userInput, passwordInput)) {
                ApplicationWindow.getInstance().setPanel(new DeskView());
            } else {
                showMessageDialog(null, "Login failed. Try again.", "Plan-It", ERROR_MESSAGE);
            }
        }
    }

    private boolean searchUser(String user, String password) {
        String query = "SELECT * FROM User WHERE (username = ? OR email = ?) AND password = ?";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Imposta i parametri per il PreparedStatement
            preparedStatement.setString(1, user); // username
            preparedStatement.setString(2, user); // email
            preparedStatement.setString(3, password); // password

            // Esegui la query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Controlla se esiste un risultato
                if (resultSet.next()) {
                    System.out.println("User found: " + resultSet.getString("username"));
                    return true; // Utente trovato
                } else {
                    System.out.println("No user found matching the criteria.");
                    return false; // Nessun utente trovato
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
            return false;
        }
    }

}
