package view.panel;

import core.GlobalResources;
import view.panel.iconPanel.Folder;
import view.panel.iconPanel.Task;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {
    private JPanel sideMenu;
    private JPanel homePanel;

    public DeskView() {
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
        splitPane.setDividerSize(0);              // Rende invisibile il divisore
        splitPane.setContinuousLayout(true);      // Aggiorna il layout durante il trascinamento
        splitPane.setBorder(BorderFactory.createEmptyBorder());  // Rimuove il bordo

        // Aggiunge lo SplitPane al pannello principale
        add(splitPane, BorderLayout.CENTER);
        addFolder();
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

    private void addFolder(){
        Folder folder1 = new Folder("Scuola");
        Folder folder2 = new Folder("Lavoro");
        Folder folder3 = new Folder("Progetto");
        Folder folder4 = new Folder("NomeMoltoLungoPerTest");
        Task task1 = new Task("Task1");
        Task task2 = new Task("Task2");
        Task task3 = new Task("Task3");
        homePanel.add(folder1);
        homePanel.add(folder2);
        homePanel.add(folder3);
        homePanel.add(folder4);

        homePanel.add(task1);
        homePanel.add(task2);
        homePanel.add(task3);
    }
}
