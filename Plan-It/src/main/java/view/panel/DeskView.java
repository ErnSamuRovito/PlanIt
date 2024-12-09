package view.panel;

import core.DataProvider;
import core.GlobalResources;
import view.panel.iconPanel.IconFactory;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {
    private JPanel sideMenu;
    private JPanel homePanel;
    private DataProvider dataProvider;

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
    }

    // Metodo per creare il pannello laterale (menu)
    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(GlobalResources.COLOR_CREMA);
        sideMenu.setPreferredSize(new Dimension(200, getHeight())); // Larghezza iniziale
        sideMenu.setLayout(new BorderLayout());

        // Aggiungi la GIF animata al side menu
        JLabel gifLabel = new JLabel(new ImageIcon(GlobalResources.plantHappyState));
        sideMenu.add(gifLabel, BorderLayout.NORTH);

        return sideMenu;
    }

    private void loadData() {
        // Recupera i folder dal data provider e li aggiunge al pannello
        for (String folder : dataProvider.getFolders()) {
            homePanel.add(IconFactory.createIconPanel("folder", folder));
        }

        // Recupera i task dal data provider e li aggiunge al pannello
        for (String task : dataProvider.getTasks()) {
            homePanel.add(IconFactory.createIconPanel("task", task));
        }
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

    private void addCreateIcon(){
        homePanel.add(IconFactory.createIconPanel("add","new"));
    }
}
