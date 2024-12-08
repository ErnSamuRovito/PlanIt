package controller.commandPattern;

import core.SqLiteConnection;
import model.FormatValidator;
import view.ApplicationWindow;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.DeskView;
import view.panel.SigninView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class SigninCommand implements ActionCommand{
    private final SigninView parentView;

    public SigninCommand(SigninView parentView) {
        this.parentView = parentView;
    }

    @Override public void execute() {
        if (parentView != null) {
            // Ottieni i campi dalla vista
            CustomTextField usernameField = parentView.getUsernameField();
            String usernameInput = usernameField.getText();

            CustomTextField emailField = parentView.getEmailField();
            String emailInput = emailField.getText();

            CustomPasswordField passwordField = parentView.getPasswordField();
            String passwordInput = passwordField.getPasswordString();

            // Validazione delle credenziali
            if (FormatValidator.isValidEmail(emailInput) && FormatValidator.isValidPassword(passwordInput)) {
                if (registerUser(usernameInput, emailInput, passwordInput)) {
                    ApplicationWindow.getInstance().setPanel(new DeskView());
                } else {
                    showMessageDialog(null, "Registration failed. Try again.", "Plan-It", ERROR_MESSAGE);
                }
            } else {
                if (!FormatValidator.isValidEmail(emailInput))
                    showMessageDialog(null, "Email isn't correct!", "Plan-It", ERROR_MESSAGE);
                if (!FormatValidator.isValidPassword(passwordInput))
                    showMessageDialog(null, "The password must have at least 6 characters!", "Plan-It", ERROR_MESSAGE);
                System.out.println("Email valida: " + FormatValidator.isValidEmail(emailInput));
                System.out.println("Password valida: " + FormatValidator.isValidPassword(passwordInput));
            }
        }
    }

    private boolean registerUser(String username, String email, String password) {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected);
                System.out.println("Query eseguita!!!!");
                return rowsAffected > 0;
        } catch (SQLException e) {
                System.err.println("Error inserting user: " + e.getMessage());
                return false;
        }
    }
}
