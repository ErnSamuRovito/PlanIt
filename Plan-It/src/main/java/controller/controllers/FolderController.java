package controller.controllers;

import model.composite.Folder;
import model.services.FolderService;

public class FolderController {
    private final FolderService folderService;
    public FolderController(final FolderService folderService) {this.folderService = folderService;}
    public int getFolderIdByNameAndOwner(String folder, String user){return folderService.getFolderIdByNameAndOwner(folder, user);}
    public Folder getFolderById(int id){return folderService.getFolderById(id);}
}
