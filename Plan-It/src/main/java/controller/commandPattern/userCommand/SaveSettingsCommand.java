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

import javax.swing.*;

public class SaveSettingsCommand implements ActionCommand {
    private final CustomTextField usernameField;
    private final CustomTextField namePlantField;
    private final CustomTextField emailField;

    public SaveSettingsCommand(CustomTextField usernameField, CustomTextField namePlantField, CustomTextField emailField) {
        this.usernameField = usernameField;
        this.namePlantField = namePlantField;
        this.emailField = emailField;
    }

    @Override
    public void execute() {
        String newName = usernameField.getText();
        String newPlantName = namePlantField.getText();
        String newEmail = emailField.getText();

        // Verifica che l'email sia valida
        if (( newEmail!=null && !newEmail.isEmpty() ) && !isValidEmail(newEmail)) {
            // Se l'email non è valida, mostra un messaggio di errore
            JOptionPane.showMessageDialog(null, "Invalid email address. Please enter a valid email.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return; // Non procedere oltre, non cambiare la schermata
        }

        // Imposta i nuovi valori se tutti sono validi
        setNewUsername(newName);
        setNewPlantUsername(newPlantName);
        setNewEmail(newEmail);

        // Aggiorna la vista solo se tutte le informazioni sono valide
        ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
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
