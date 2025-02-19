package controller.commandPattern.userCommand;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import core.SqLiteConnection;
import model.FormatValidator;
import model.PasswordUtils;
import model.User;
import model.dao.user.UserDAO;
import model.dao.user.UserDAOImpl;
import view.UICreationalPattern.UIComponents.CustomPasswordField;

import javax.swing.*;
import java.util.Arrays;

public class ChangePasswordCommand implements ActionCommand {
    private final CustomPasswordField currentPasswordField;
    private final CustomPasswordField passwordField;
    private final CustomPasswordField confirmPasswordField;

    public ChangePasswordCommand(CustomPasswordField currentPasswordField,
                                 CustomPasswordField passwordField,
                                 CustomPasswordField confirmPasswordField) {
        this.currentPasswordField = currentPasswordField;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
    }

    @Override
    public void execute() {
        char[] currentPassword = currentPasswordField.getPassword();
        char[] newPassword = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        try {
            // Ottieni la password hashata dal database
            UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
            String hashedPassword = userDAO.getHashedPassword(User.getInstance().getId());

            // Verifica se la password corrente è corretta (usando verifyPassword con la password in chiaro)
            if (!PasswordUtils.verifyPassword(new String(currentPassword), hashedPassword)) {
                JOptionPane.showMessageDialog(null, "The current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica che la nuova password sia diversa dalla corrente
            if (Arrays.equals(currentPassword, newPassword)) {
                JOptionPane.showMessageDialog(null, "The new password must be different from the current password.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica se le password corrispondono
            if (!Arrays.equals(newPassword, confirmPassword)) {
                JOptionPane.showMessageDialog(null, "The passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String newPasswordString = new String(newPassword);

            // Verifica che la nuova password sia valida
            if (FormatValidator.isValidEmail(newPasswordString) &&
                    !FormatValidator.isValidPassword(newPasswordString)) {
                JOptionPane.showMessageDialog(null, "The new password is not valid.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Imposta la nuova password (esegui l'hashing della nuova password)
            userDAO.setPassword(User.getInstance().getId(), newPasswordString);

            // Cambia il pannello della vista
            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
            JOptionPane.showMessageDialog(null, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error changing password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
