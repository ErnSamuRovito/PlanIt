package controller.commandPattern;

import core.SqLiteConnection;
import model.FormatValidator;
import model.User;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIComponents.CustomPasswordField;

import java.util.Arrays;

public class ChangePasswordCommand implements ActionCommand {
    private final CustomPasswordField passwordField;
    private final CustomPasswordField confirmPasswordField;

    public ChangePasswordCommand(CustomPasswordField passwordField,
                                 CustomPasswordField confirmPasswordField) {
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
    }

    @Override
    public void execute() {
        char[] password = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        try {
            if (!Arrays.equals(password, confirmPassword)) {
                System.out.println("Le password non corrispondono.");
                return;
            }

            String passwordString = new String(password);
            if (!FormatValidator.isValidPassword(passwordString)) {
                System.out.println("La password non è valida.");
                return;
            }

            TaskDAOImpl taskDAO = new TaskDAOImpl(SqLiteConnection.getInstance().getConnection());
            taskDAO.setPassword(User.getInstance().getId(), passwordString);

            System.out.println("Password cambiata con successo.");
        } catch (Exception e) {
            System.err.println("Errore durante il cambio della password: " + e.getMessage());
        }
    }
}
