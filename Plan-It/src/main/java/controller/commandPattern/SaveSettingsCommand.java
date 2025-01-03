package controller.commandPattern;

import core.SqLiteConnection;
import model.User;
import model.dao.avatarPlant.AvatarPlantDAO;
import model.dao.avatarPlant.AvatarPlantDAOImpl;
import model.dao.user.UserDAO;
import model.dao.user.UserDAOImpl;
import model.plant.AvatarPlant;
import view.UICreationalPattern.UIComponents.CustomTextField;

public class SaveSettingsCommand implements ActionCommand {
    private final CustomTextField usernameField;
    private final CustomTextField namePlantField;

    public SaveSettingsCommand(CustomTextField usernameField, CustomTextField namePlantField) {
        this.usernameField = usernameField;
        this.namePlantField = namePlantField;
    }

    @Override
    public void execute() {
        String newName = usernameField.getText();
        String newPlantName = namePlantField.getText();
        setNewUsername(newName);
        setNewPlantUsername(newPlantName);
    }

    private void setNewUsername(String newUsername) {
        UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
        if (newUsername != null && !newUsername.isEmpty() && !newUsername.equals(User.getInstance().getUsername())) {
            userDAO.setUsername(newUsername);
            System.out.println("New Username : " + newUsername);
        }
    }

    private void setNewPlantUsername(String newPlantName) {
        AvatarPlantDAO avatarPlantDAO = new AvatarPlantDAOImpl(SqLiteConnection.getInstance().getConnection());
        if (newPlantName != null && !newPlantName.isEmpty() && !newPlantName.equals(AvatarPlant.getInstance().getName())) {
            avatarPlantDAO.setName(newPlantName);
            System.out.println("New PlantName : " + newPlantName);

        }
    }
}
