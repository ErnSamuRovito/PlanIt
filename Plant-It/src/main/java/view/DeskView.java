// Package: view

package view;

import core.GlobalResources;
import core.ComponentManager; // Importa ComponentManager
import view.factory.ButtonFactory; // Importa ButtonFactory

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

        // Crea il pulsante "Torna alla Login" utilizzando la factory
        CustomButton backButton = new ButtonFactory("Torna alla Login")
                .setSize(new Dimension(150, 40)) // Imposta la dimensione del pulsante
                .setActionListener(e -> {
                    // Associa l'azione di ritorno alla LoginView
                    ComponentManager.getInstance().setPanel(ComponentManager.getInstance().getLoginView());
                })
                .create();

        sideMenu.add(backButton); // Aggiungi il pulsante al menu laterale

        // Posiziona il menu sul lato sinistro
        add(sideMenu, BorderLayout.WEST);
    }
}
