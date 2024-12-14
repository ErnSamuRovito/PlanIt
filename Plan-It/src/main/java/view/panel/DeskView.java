package view.panel;

import controller.commandPattern.ExploreFolderCommand;
import controller.commandPattern.GoBackCommand;
import controller.commandPattern.GoToTaskViewCommand;
import core.ComponentManager;
import core.DatabaseFileLoader;
import core.GlobalResources;
import model.Plant.AvatarPlant;
import view.panel.createPannel.CreatePanel;
import view.panel.createPannel.FolderCreateDecorator;
import view.panel.createPannel.TaskCreateDecorator;
import view.panel.iconPanel.IconFactory;
import view.panel.iconPanel.IconPanel;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {
    private JPanel sideMenu;
    private JPanel homePanel;
    private DatabaseFileLoader databaseFileLoader;

    private IconPanel iconPanelAdd, iconPanelBack;

    public DeskView(DatabaseFileLoader databaseFileLoader) {
        this.databaseFileLoader = databaseFileLoader;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        // Crea i pannelli
        sideMenu = createSideMenu();
        homePanel = new JPanel();
        homePanel.setBackground(GlobalResources.COLOR_PANNA);
        homePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Crea lo SplitPane per rendere regolabili i pannelli
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sideMenu, homePanel);
        splitPane.setDividerLocation(200);        // Posizione iniziale del divisore
        splitPane.setDividerSize(3);              // Rende invisibile il divisore
        splitPane.setContinuousLayout(true);      // Aggiorna il layout durante il trascinamento
        splitPane.setBorder(BorderFactory.createEmptyBorder());  // Rimuove il bordo

        // Aggiunge lo SplitPane al pannello principale
        add(splitPane, BorderLayout.CENTER);

        addBackIcon();
        loadData();
        addCreateIcon();
        addPopupMenu();
    }

    // Metodo per creare il pannello laterale (menu)
    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(GlobalResources.COLOR_CREMA);
        sideMenu.setPreferredSize(new Dimension(200, getHeight())); // Larghezza iniziale
        sideMenu.setLayout(new BorderLayout());

        // Aggiungi la GIF animata al side menu
        JLabel gifLabel = new JLabel(new ImageIcon(AvatarPlant.getInstance().getPathGifImage()));
        System.out.println("state pianta : " + AvatarPlant.getInstance().getState());
        System.out.println("path pianta : " + AvatarPlant.getInstance().getPathGifImage());
        sideMenu.add(gifLabel, BorderLayout.NORTH);

        return sideMenu;
    }

    private void loadData() {
        // Recupera i folder dal data provider e li aggiunge al pannello
        for (String folder : databaseFileLoader.getFolders()) {
            //creo le istanze di IconPanel e gli attacco il comando, che riceve:
            // - il nome dell'utente a cui appartiene
            // - il nome della cartella
            homePanel.add(
                IconFactory.createIconPanel(
                    "folder", folder, new ExploreFolderCommand(databaseFileLoader.getUser(), folder)
                )
            );
        }

        // Recupera i task dal data provider e li aggiunge al pannello
        for (String task : databaseFileLoader.getTasks()) {
            homePanel.add(IconFactory.createIconPanel(
                "task", task, new GoToTaskViewCommand(task,databaseFileLoader.getStartFolder()))
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

    private void addCreateIcon(){
        iconPanelAdd = IconFactory.createIconPanel("add","new",null);
        homePanel.add(iconPanelAdd);
    }

    private void addBackIcon(){
        if (!databaseFileLoader.getStartFolder().equals("/root")) {
            iconPanelBack = IconFactory.createIconPanel(
                    "back",
                    "back",
                    new GoBackCommand(databaseFileLoader.getUser(), databaseFileLoader.getStartFolder())
            );
            homePanel.add(iconPanelBack);
        }
    }
}
