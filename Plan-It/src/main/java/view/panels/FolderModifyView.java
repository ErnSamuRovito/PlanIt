package view.panels;

import controller.commandPattern.componentCommands.ModifyFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import controller.controllers.FolderController;
import model.services.FolderService;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class FolderModifyView extends TemplateView {

    // Costanti per indicizzare i componenti
    private static final int FOLDER_NAME_TEXTFIELD_INDEX = 1;

    private final FolderService folderService;
    private final FolderController folderController;
    private final String user;
    private final String folder;
    private final int folderId;

    public FolderModifyView(String user, String folder) {
        this.user = user;
        this.folder = folder;

        // Inizializzazione servizi e controller
        this.folderService = new FolderService();
        this.folderController = new FolderController(folderService);
        this.folderId = folderController.getFolderIdByNameAndOwner(folder, user);

        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        // Creazione dei componenti
        UIBuilder folderNameLabelBuilder = registry.getFactory("Label").createBuild();
        folderNameLabelBuilder.text("Folder name");

        UIBuilder folderNameTextFieldBuilder = registry.getFactory("TextField").createBuild();
        folderNameTextFieldBuilder.text(folder);

        UIBuilder modifyFolderButtonBuilder = registry.getFactory("Button").createBuild();
        modifyFolderButtonBuilder
                .text("Modify")
                .action(new ModifyFolderCommand(this));

        UIBuilder backLabelBuilder = registry.getFactory("ClickableLabel").createBuild();
        backLabelBuilder
                .text("Back")
                .action(new GoToDeskViewCommand());

        // Aggiunta dei componenti in ordine
        builders.add(folderNameLabelBuilder);     // 0: Etichetta "Folder name"
        builders.add(folderNameTextFieldBuilder); // 1: Campo di testo "Folder name"
        builders.add(modifyFolderButtonBuilder);  // 2: Bottone "Modify"
        builders.add(backLabelBuilder);           // 3: Etichetta cliccabile "Back"
    }

    @Override
    protected void addComponentsToPanel() {
        // Costruisce e aggiunge i componenti al pannello con il layout corretto
        constructBuilders(builders);
        for (int i = 0; i < components.size(); i++) {
            gbc.gridy = i; // Posiziona il componente nella riga corrispondente
            add((Component) components.get(i), gbc);
        }
    }

    /**
     * Ottiene il nome della cartella dal campo di testo.
     *
     * @return Nome della cartella.
     */
    public String getFolderName() {
        return ((CustomTextField) components.get(FOLDER_NAME_TEXTFIELD_INDEX)).getText();
    }

    /**
     * Ottiene l'ID della cartella.
     *
     * @return ID della cartella.
     */
    public int getFolderId() {
        return folderId;
    }
}
