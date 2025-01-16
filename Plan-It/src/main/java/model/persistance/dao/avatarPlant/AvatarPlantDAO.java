package model.persistance.dao.avatarPlant;

import java.util.List;

public interface AvatarPlantDAO {
    Boolean addPlant(AvatarPlantDB plant);
    AvatarPlantDB getPlantById(int id);
    List<AvatarPlantDB> getPlantsByOwnerId(int ownerId);
    List<AvatarPlantDB> getPlantsByOwnerName(String ownerName);
    List<AvatarPlantDB> getAllPlants();
    void setName(String name);
    void updatePlant(AvatarPlantDB plant);
    void deletePlant(int id);
}
