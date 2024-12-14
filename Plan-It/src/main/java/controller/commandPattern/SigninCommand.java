package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.FormatValidator;
import model.Plant.AvatarPlant;
import model.Plant.NormalState;
import model.Plant.SadState;
import model.User;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.SigninView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class SigninCommand implements ActionCommand {
    private static final String ERROR_TITLE = "Plan-It";
    private static final String ERROR_REGISTRATION_FAILED = "Registration failed. Try again.";
    private static final String ERROR_USER_ID_RETRIEVAL = "Failed to retrieve user ID.";
    private static final String ERROR_EMAIL_INVALID = "Email isn't correct!";
    private static final String ERROR_PASSWORD_INVALID = "The password must have at least 6 characters!";
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

        String usernameInput = parentView.getUsernameField().getText();
        String emailInput = parentView.getEmailField().getText();
        String passwordInput = parentView.getPasswordField().getPasswordString();

        if (!FormatValidator.isValidEmail(emailInput)) {
            showMessageDialog(null, ERROR_EMAIL_INVALID, ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        if (!FormatValidator.isValidPassword(passwordInput)) {
            showMessageDialog(null, ERROR_PASSWORD_INVALID, ERROR_TITLE, ERROR_MESSAGE);
            return;
        }

        int userId = registerUserAndGetId(usernameInput, emailInput, passwordInput);

        User.getInstance().loadUser(userId);
        System.out.println(User.getInstance().toString());
        AvatarPlant.getInstance().loadPlant(userId);
        System.out.println("HP PIANTINA : " + AvatarPlant.getInstance().getHp());

        if (userId != -1) {
            // Crea la piantina assocuata all'utente
            if (createPlant("Piantina di " + usernameInput, userId)) {
                System.out.println(SUCCESS_PLANT_CREATION);
            } else {
                System.err.println(ERROR_PLANT_CREATION);
            }

            // Crea la cartella 'root' associata all'utente
            if (createRootFolder(userId)){System.out.println(SUCCESS_PLANT_CREATION);}
            else{System.err.println(ERROR_ROOT_FOLDER_CREATION);}

            ComponentManager.getInstance().setPanel(
                    ComponentManager.getInstance().getDeskView(usernameInput, "/root")
            );
        } else {
            showMessageDialog(null, ERROR_REGISTRATION_FAILED, ERROR_TITLE, ERROR_MESSAGE);
        }
    }

    private int registerUserAndGetId(String username, String email, String password) {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
        return -1;
    }

    private boolean createPlant(String name, int owner) {
        String query = "INSERT INTO AvatarPlant (name, hp, owner) VALUES (?, ?, ?)";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, 100);
            preparedStatement.setInt(3, owner);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting plant: " + e.getMessage());
            return false;
        }
    }

    private boolean createRootFolder(int userId){
        String query = "INSERT INTO Folder (folder_name, owner) VALUES (?, ?)";
        try (Connection connection = SqLiteConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "root");
            preparedStatement.setInt(2, userId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error creating root folder: " + e.getMessage());
            return false;
        }
    }
}
