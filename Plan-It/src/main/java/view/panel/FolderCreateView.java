package view.panel;

import controller.commandPattern.CreateFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import view.UICreationalPattern.UIBuilders.UIBuilder;
import view.UICreationalPattern.UIComponentFactoryRegistry;
import view.UICreationalPattern.UIComponents.CustomTextField;

import java.awt.*;

public class FolderCreateView extends TemplateView {

    // Costante per l'indice del campo di testo del nome della cartella
    private static final int FOLDER_NAME_TEXTFIELD_INDEX = 1;

    public FolderCreateView() {
        initialize();
    }

    @Override
    protected void createComponents() {
        UIComponentFactoryRegistry registry = UIComponentFactoryRegistry.getInstance();

        // Creazione dell'etichetta "Folder name"
        UIBuilder folderNameLabelBuilder = registry.getFactory("Label").createBuild();
        folderNameLabelBuilder.text("Folder name");

        // Creazione del campo di testo per il nome della cartella
        UIBuilder folderNameTextFieldBuilder = registry.getFactory("TextField").createBuild();
        folderNameTextFieldBuilder.placeholder("Folder name");

        // Creazione del pulsante "Create"
        UIBuilder createFolderButtonBuilder = registry.getFactory("Button").createBuild();
        createFolderButtonBuilder
                .text("Create")
                .action(new CreateFolderCommand(this));

        // Creazione dell'etichetta cliccabile "Back"
        UIBuilder backLabelBuilder = registry.getFactory("ClickableLabel").createBuild();
        backLabelBuilder
                .text("Back")
                .action(new GoToDeskViewCommand());

        // Aggiunta dei componenti in ordine
        builders.add(folderNameLabelBuilder);      // 0: Etichetta "Folder name"
        builders.add(folderNameTextFieldBuilder); // 1: Campo di testo "Folder name"
        builders.add(createFolderButtonBuilder);   // 2: Pulsante "Create"
        builders.add(backLabelBuilder);            // 3: Etichetta cliccabile "Back"
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
}
