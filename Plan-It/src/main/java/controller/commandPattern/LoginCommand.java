package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.Plant.AvatarPlant;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
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

            int idLogged = searchUserAndGetId(userInput, passwordInput);

            if (idLogged != -1) {
                AvatarPlant.getInstance().loadPlant(idLogged);
                AvatarPlant.getInstance().updateState();
                ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getDeskView(userInput,"/root"));

                System.out.println("HP PIANTINA : " + AvatarPlant.getInstance().getHp());
            } else {
                showMessageDialog(null, "Login failed. Try again.", "Plan-It", ERROR_MESSAGE);
            }
        }
    }

    private int searchUserAndGetId(String user, String password) {
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
                    return resultSet.getInt(1);
                } else {
                    System.out.println("No user found matching the criteria.");
                    return -1; // Nessun utente trovato
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching for user: " + e.getMessage());
            return -1;
        }
    }
}
