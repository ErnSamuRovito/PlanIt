package view.panel;

import controller.commandPattern.ExploreFolderCommand;
import controller.commandPattern.GoBackCommand;
import controller.commandPattern.GoToTaskViewCommand;
import core.ComponentManager;
import core.DatabaseFileLoader;
import core.GlobalResources;
import view.SplitPanel;
import view.panel.createPannel.CreatePanel;
import view.panel.createPannel.FolderCreateDecorator;
import view.panel.createPannel.TaskCreateDecorator;
import view.panel.iconPanel.IconFactory;
import view.panel.iconPanel.IconPanel;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {
    private SplitPanel splitPanel;
    private DatabaseFileLoader databaseFileLoader;

    private IconPanel iconPanelAdd, iconPanelBack;

    public DeskView(DatabaseFileLoader databaseFileLoader) {
        this.databaseFileLoader = databaseFileLoader;

        // Imposta il layout principale
        setLayout(new BorderLayout());

        // Inizializza l'interfaccia utente
        initializeUI();
    }

    private void initializeUI() {
        // Crea e configura lo SplitPanel
        splitPanel = new SplitPanel();

        // Configura icone e dati
        addBackIcon();
        loadData();
        addCreateIcon();
        addPopupMenu();

        // Aggiungi lo SplitPanel al centro del layout
        add(splitPanel, BorderLayout.CENTER);
    }

    private void loadData() {
        // Recupera i folder dal data provider e li aggiunge al pannello
        for (String folder : databaseFileLoader.getFolders()) {
            splitPanel.getHomePanel().add(
                    IconFactory.createIconPanel(
                            "folder", folder, new ExploreFolderCommand(databaseFileLoader.getUser(), folder)
                    )
            );
        }

        // Recupera i task dal data provider e li aggiunge al pannello
        for (String task : databaseFileLoader.getTasks()) {
            splitPanel.getHomePanel().add(IconFactory.createIconPanel(
                    "task", task, new GoToTaskViewCommand(task, databaseFileLoader.getStartFolder()))
            );
        }
    }

    private void addPopupMenu() {
        // Creazione del menu a comparsa
        JPopupMenu popupMenu = new JPopupMenu();

        // Creazione delle voci del menu per la creazione di folder o task
        JMenuItem createFolderItem = new JMenuItem("Create Folder");
        JMenuItem createTaskItem = new JMenuItem("Create Task");

        // Aggiungi le voci al menu a comparsa
        popupMenu.add(createFolderItem);
        popupMenu.add(createTaskItem);

        // Aggiungi gli action listener per ciascuna voce
        createFolderItem.addActionListener(e -> createFolder());
        createTaskItem.addActionListener(e -> createTask());

        // Imposta il menu a comparsa sul pannello home
        iconPanelAdd.setComponentPopupMenu(popupMenu);
    }

    private void createTask() {
        CreatePanel createPanel = new CreatePanel();
        createPanel = new TaskCreateDecorator(createPanel);
        ComponentManager.getInstance().setPanel(createPanel);
    }

    private void createFolder() {
        CreatePanel createPanel = new CreatePanel();
        createPanel = new FolderCreateDecorator(createPanel);
        ComponentManager.getInstance().setPanel(createPanel);
    }

    private void addCreateIcon() {
        iconPanelAdd = IconFactory.createIconPanel("add", "new", null);
        splitPanel.getHomePanel().add(iconPanelAdd);
    }

    private void addBackIcon() {
        if (!databaseFileLoader.getStartFolder().equals("/root")) {
            iconPanelBack = IconFactory.createIconPanel(
                    "back",
                    "back",
                    new GoBackCommand(databaseFileLoader.getUser(), databaseFileLoader.getStartFolder())
            );
            splitPanel.getHomePanel().add(iconPanelBack);
        }
    }
}
