// Package: view

package view.panel;

import core.GlobalResources;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {

    public DeskView() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        // Aggiunge il menù laterale
        addSideMenu();
    }

    // Metodo per creare e aggiungere il pannello laterale (menu)
    private void addSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(GlobalResources.COLOR_CREMA); // Colore del menu
        sideMenu.setPreferredSize(new Dimension(200, getHeight())); // Larghezza fissa di 200 px

        // Posiziona il menu sul lato sinistro
        add(sideMenu, BorderLayout.WEST);
    }
}
