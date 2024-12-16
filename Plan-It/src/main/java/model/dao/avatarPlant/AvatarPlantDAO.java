package model.dao.avatarPlant;

import java.util.List;

public interface AvatarPlantDAO {
    void addPlant(AvatarPlant plant);
    AvatarPlant getPlantById(int id);
    List<AvatarPlant> getPlantsByOwner(int ownerId);
    List<AvatarPlant> getAllPlants();
    void updatePlant(AvatarPlant plant);
    void deletePlant(int id);
}
