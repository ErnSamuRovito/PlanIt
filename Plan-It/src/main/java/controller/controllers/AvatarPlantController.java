package controller.controllers;

import model.dao.avatarPlant.AvatarPlantDB;
import model.services.AvatarPlantService;

public class AvatarPlantController {
    private final AvatarPlantService avatarPlantService;

    public AvatarPlantController(AvatarPlantService avatarPlantService) {
        this.avatarPlantService = avatarPlantService;
    }

    public AvatarPlantDB getPlantByOwnerId(int ownerId){
        return avatarPlantService.getPlantByOwnerId(ownerId);
    }
}
