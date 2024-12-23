package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.FormatValidator;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.avatarPlant.AvatarPlantDB;
import model.dao.folder.FolderDAOImpl;
import model.dao.user.UserDAOImpl;
import model.plant.AvatarPlant;
import model.User;
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

        int userId;
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            UserDAOImpl userDAO=new UserDAOImpl(connection);
            userId=userDAO.registerUserAndGetId(usernameInput, emailInput, passwordInput);
        } catch (SQLException e) {throw new RuntimeException(e);}

        User.getInstance().loadUser(userId);
        System.out.println(User.getInstance().toString());

        if (userId != -1) {
            // Crea la cartella 'root' associata all'utente
            if (createRootFolder(userId)){
                System.out.println(SUCCESS_PLANT_CREATION);

                // Crea la piantina assocuata all'utente
                if (createPlant("Piantina di " + usernameInput, userId)) {
                    System.out.println(SUCCESS_PLANT_CREATION);
                    AvatarPlant.getInstance().loadPlant(userId);
                    AvatarPlant.getInstance().updateState();
                    System.out.println("HP PIANTINA : " + AvatarPlant.getInstance().getHp());
                } else {
                    System.err.println(ERROR_PLANT_CREATION);
                }

                ComponentManager.getInstance().setUserAndPath(usernameInput, "/root");
                ComponentManager.getInstance().setPanel(
                        ComponentManager.getInstance().getDeskView()
                );
            }
            else{System.err.println(ERROR_ROOT_FOLDER_CREATION);}
        } else {
            showMessageDialog(null, ERROR_REGISTRATION_FAILED, ERROR_TITLE, ERROR_MESSAGE);
        }
    }

    private boolean createPlant(String name, int owner) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            AvatarPlantDB avatarPlant=new AvatarPlantDB(0,name,100,owner);
            return avatarPlantDAO.addPlant(avatarPlant);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean createRootFolder(int userId){
        try (Connection connection = SqLiteConnection.getInstance().getConnection()){
            FolderDAOImpl folderDAO=new FolderDAOImpl(connection);
            return folderDAO.addRootFolder(userId);
        } catch (SQLException e) {throw new RuntimeException(e);}
    }
}
