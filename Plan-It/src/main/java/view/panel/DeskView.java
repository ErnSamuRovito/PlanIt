package view.panel;

import controller.commandPattern.ExploreFolderCommand;
import core.ComponentManager;
import core.DataProvider;
import core.GlobalResources;
import model.Plant.AvatarPlant;
import view.panel.iconPanel.IconFactory;
import view.panel.iconPanel.IconPanel;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {
    private JPanel sideMenu;
    private JPanel homePanel;
    private DataProvider dataProvider;

    IconPanel iconPanelAdd;

    public DeskView(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
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
        for (String folder : dataProvider.getFolders()) {
            //creo le istanze di IconPanel e gli attacco il comando, che riceve:
            // - il nome dell'utente a cui appartiene
            // - il nome della cartella
            homePanel.add(
                IconFactory.createIconPanel(
                    "folder", folder, new ExploreFolderCommand(dataProvider.getUser(), folder)
                )
            );
        }

        // Recupera i task dal data provider e li aggiunge al pannello
        //for (String task : dataProvider.getTasks()) {
        //    homePanel.add(IconFactory.createIconPanel("task", task));
        //}
    }

    private void addFolder(){
        /*
        homePanel.add(IconFactory.createIconPanel("folder","Home"));
        homePanel.add(IconFactory.createIconPanel("folder","Work"));
        homePanel.add(IconFactory.createIconPanel("folder","Project"));
        homePanel.add(IconFactory.createIconPanel("task","task1"));
        homePanel.add(IconFactory.createIconPanel("task","task2"));
        homePanel.add(IconFactory.createIconPanel("task","task3"));
        */
    }

    private void addPopupMenu() {
        // Creazione del menu a comparsa
        JPopupMenu popupMenu = new JPopupMenu();

        // Creazione delle voci del menu per la creazione di folder o task
        JMenuItem createFolderItem = new JMenuItem("Crea Cartella");
        JMenuItem createTaskItem = new JMenuItem("Crea Task");

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
        CreatePanel cp = new CreatePanel();
        cp = new TaskCreateDecorator(cp);
        ComponentManager.getInstance().setPanel(cp);
    }

    private void createFolder() {
        CreatePanel cp = new CreatePanel();
        cp = new FolderCreateDecorator(cp);
        ComponentManager.getInstance().setPanel(cp);
    }

    private void addCreateIcon(){
        iconPanelAdd = IconFactory.createIconPanel("add","new",null);
        homePanel.add(iconPanelAdd);
    }
}
