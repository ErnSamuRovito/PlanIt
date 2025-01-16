package model.services;

import model.persistance.SqLiteConnection;
import model.persistance.dao.avatarPlant.AvatarPlantDAOImpl;
import model.persistance.dao.avatarPlant.AvatarPlantDB;

import java.sql.Connection;
import java.sql.SQLException;

public class AvatarPlantService {
    public AvatarPlantDB getPlantByOwnerId(int ownerId) {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            AvatarPlantDAOImpl avatarPlantDAO = new AvatarPlantDAOImpl(connection);
            return avatarPlantDAO.getPlantsByOwnerId(ownerId).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
