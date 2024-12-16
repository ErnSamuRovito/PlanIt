package model.dao.avatarPlant;

import java.util.List;

public interface AvatarPlantDAO {
    void addPlant(AvatarPlantDB plant);
    AvatarPlantDB getPlantById(int id);
    List<AvatarPlantDB> getPlantsByOwner(int ownerId);
    List<AvatarPlantDB> getAllPlants();
    void updatePlant(AvatarPlantDB plant);
    void deletePlant(int id);
}
