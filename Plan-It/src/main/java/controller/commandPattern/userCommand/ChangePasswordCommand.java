package controller.commandPattern.userCommand;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import core.SqLiteConnection;
import model.FormatValidator;
import model.PasswordUtils;
import model.User;
import model.dao.task.TaskDAOImpl;
import model.dao.user.UserDAO;
import model.dao.user.UserDAOImpl;
import view.UICreationalPattern.UIComponents.CustomPasswordField;

import java.security.MessageDigest;
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
            UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
            String hashedPassword = userDAO.getHashedPassword(User.getInstance().getId());

            if (PasswordUtils.verifyPassword(Arrays.toString(currentPassword), hashedPassword)) {
                System.out.println("La password attuale non è corretta.");
                return;
            } else if (PasswordUtils.verifyPassword(Arrays.toString(newPassword), hashedPassword)) {
                System.out.println("La nuova password deve essere diversa dalla password attuale.");
                return;
            }

            if (!Arrays.equals(newPassword, confirmPassword)) {
                System.out.println("Le password non corrispondono.");
                return;
            }

            String newPasswordString = new String(newPassword);

            if (FormatValidator.isValidEmail(newPasswordString) &&
                    !FormatValidator.isValidPassword(newPasswordString)) {
                System.out.println("La nuova password non è valida.");
                return;
            }

            if (!Arrays.equals(newPassword, confirmPassword)) {
                System.out.println("Non hai confermato correttamente la password.");
                return;
            }

            TaskDAOImpl taskDAO = new TaskDAOImpl(SqLiteConnection.getInstance().getConnection());
            taskDAO.setPassword(User.getInstance().getId(), newPasswordString);

            ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
            System.out.println("Password cambiata con successo.");
        } catch (Exception e) {
            System.err.println("Errore durante il cambio della password: " + e.getMessage());
        }
    }
}
