package controller.commandPattern.userCommand;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import core.SqLiteConnection;
import model.utils.FormatValidator;
import model.User;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.avatarPlant.AvatarPlantDB;
import model.dao.folder.FolderDAOImpl;
import model.dao.user.UserDAOImpl;
import model.plantStates.AvatarPlant;
import view.panel.SigninView;

import java.sql.Connection;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class SigninCommand implements ActionCommand {
    private static final String ERROR_TITLE = "Plan-It";
    private static final String ERROR_REGISTRATION_FAILED = "Registration failed. Try again.";
    private static final String ERROR_USER_ID_RETRIEVAL = "Failed to retrieve user ID.";
    private static final String ERROR_EMAIL_INVALID = "Email isn't correct!";
    private static final String ERROR_PASSWORD_INVALID = "The password must have at least 6 characters!";
    private static final String ERROR_PASSWORDS_MISMATCH = "Passwords do not match!";
    private static final String SUCCESS_PLANT_CREATION = "Piantina creata con successo!";
    private static final String ERROR_PLANT_CREATION = "Errore nella creazione della piantina.";
    private static final String ERROR_ROOT_FOLDER_CREATION = "Errore nella creazione della cartella root.";

    private final SigninView parentView;

    public SigninCommand(SigninView parentView) {
        this.parentView = parentView;
    }

    @Override
    public void execute() {
        if (parentView == null) {
            return;
        }

        String usernameInput = parentView.getUsername();
        String emailInput = parentView.getEmail();
        String passwordInput = parentView.getPassword();
        String confirmPasswordInput = parentView.getConfirmPassword();

        // Controlla se l'email è già utilizzata
        if (isEmailTaken(emailInput)) {
            showMessageDialog(null, "This email is already registered.", ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        // Controlla se l'username è già utilizzato
        if (isUsernameTaken(usernameInput)) {
            showMessageDialog(null, "This username is already taken.", ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        // Controlla la validità dell'email
        if (!FormatValidator.isValidEmail(emailInput)) {
            showMessageDialog(null, ERROR_EMAIL_INVALID, ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        // Controlla la validità della password
        if (!FormatValidator.isValidPassword(passwordInput)) {
            showMessageDialog(null, ERROR_PASSWORD_INVALID, ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        // Verifica che le password corrispondano
        if (!doPasswordsMatch(passwordInput, confirmPasswordInput)) {
            showMessageDialog(null, ERROR_PASSWORDS_MISMATCH, ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        // Registrazione dell'utente nel database
        int userId = registerUser(usernameInput, emailInput, passwordInput);
        User.getInstance().loadUser(userId);

        if (userId != -1) {
            // Crea la cartella 'root' associata all'utente
            if (createRootFolder(userId)) {
                // Crea la piantina associata all'utente
                if (createPlant(usernameInput + "'s Plant", userId)) {
                    System.out.println(SUCCESS_PLANT_CREATION);
                    AvatarPlant.getInstance().loadPlant(userId);
                    AvatarPlant.getInstance().updateState();
                } else {
                    System.err.println(ERROR_PLANT_CREATION);
                }

                ComponentManager.getInstance().setPath(usernameInput, "/root");
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView());
            } else {
                System.err.println(ERROR_ROOT_FOLDER_CREATION);
            }
        } else {
            showMessageDialog(null, ERROR_REGISTRATION_FAILED, ERROR_TITLE, ERROR_MESSAGE);
        }
    }

    private boolean isUsernameTaken(String username) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            return userDAO.isUsernameTaken(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Se si verifica un errore nella verifica, consideriamo l'username come già preso
        }
    }

    private boolean isEmailTaken(String email) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            return userDAO.isEmailTaken(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Se si verifica un errore nella verifica, consideriamo l'email come già presa
        }
    }


    private boolean createPlant(String name, int owner) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            AvatarPlantDB avatarPlant = new AvatarPlantDB(0, name, 100, owner);
            return avatarPlantDAO.addPlant(avatarPlant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean createRootFolder(int userId) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            return folderDAO.addRootFolder(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int registerUser(String username, String email, String password) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            return userDAO.registerUserAndGetId(username, email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Verifica che le password corrispondano
    private boolean doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
