package controller.commandPattern.userCommand;

import controller.commandPattern.ActionCommand;
import core.ComponentManager;
import core.SqLiteConnection;
import model.User;
import model.dao.avatarPlant.AvatarPlantDAO;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.user.UserDAO;
import model.dao.user.UserDAOImpl;
import model.plant.AvatarPlant;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.SettingsView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class SaveSettingsCommand implements ActionCommand {
    private final SettingsView parentView;
    private String newName, newEmail, newPlantName;

    public SaveSettingsCommand(SettingsView parentView) {
        this.parentView = parentView;
    }

    @Override
    public void execute() {
        this.newName=parentView.getUsername();
        this.newEmail=parentView.getEmail();
        this.newPlantName=parentView.getPlantName();

        // Verifica se l'email è già utilizzata
        if (newEmail != null && !newEmail.isEmpty() && isEmailTaken(newEmail)) {
            JOptionPane.showMessageDialog(null, "This email is already registered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica se l'username è già utilizzato
        if (newName != null && !newName.isEmpty() && isUsernameTaken(newName)) {
            JOptionPane.showMessageDialog(null, "This username is already taken.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica che l'email sia valida
        if ((newEmail != null && !newEmail.isEmpty()) && !isValidEmail(newEmail)) {
            JOptionPane.showMessageDialog(null, "Invalid email address. Please enter a valid email.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Imposta i nuovi valori se tutti sono validi
        setNewUsername(newName);
        setNewPlantUsername(newPlantName);
        setNewEmail(newEmail);

        // Aggiorna la vista solo se tutte le informazioni sono valide
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
    }

    // Metodo per verificare se l'email è già utilizzata
    private boolean isEmailTaken(String email) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            return userDAO.isEmailTaken(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Se c'è un errore, consideriamo l'email già presa
        }
    }

    // Metodo per verificare se l'username è già utilizzato
    private boolean isUsernameTaken(String username) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(connection);
            return userDAO.isUsernameTaken(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Se c'è un errore, consideriamo l'username già preso
        }
    }

    private void setNewUsername(String newUsername) {
        UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
        if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(User.getInstance().getUsername())) {
            userDAO.setUsername(newUsername);
        }
    }

    private void setNewPlantUsername(String newPlantName) {
        AvatarPlantDAO avatarPlantDAO = new AvatarPlantDAOImpl(SqLiteConnection.getInstance().getConnection());
        if (newPlantName != null && !newPlantName.isEmpty() && !newPlantName.equals(AvatarPlant.getInstance().getName())) {
            avatarPlantDAO.setName(newPlantName);
        }
    }

    private void setNewEmail(String newEmail) {
        UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
        if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(User.getInstance().getEmail())) {
            userDAO.setEmail(newEmail); // Aggiorna l'email
        }
    }

    // Metodo che valida l'email tramite una regex
    private boolean isValidEmail(String email) {
        // Regex semplice per validare un'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
}
