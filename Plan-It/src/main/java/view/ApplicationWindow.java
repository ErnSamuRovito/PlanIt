// Package: view

package view;

import core.SqLiteConnection;
import model.DesktopNotification;
import view.panel.LoginView;

import javax.swing.*;
import java.sql.*;

public class ApplicationWindow {
    private static ApplicationWindow instance;
    private final JFrame frame;

    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 800;
    private static final String DEFAULT_TITLE = "Plan-It";

    private ApplicationWindow() {
        frame = new JFrame(DEFAULT_TITLE);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta la posizione al centro dello schermo
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Imposta il pannello iniziale (LoginView)
        setPanel(new LoginView());

        DesktopNotification desktopNotification = new DesktopNotification();
        DesktopNotification.sendNotification("ciao", "sono plan-it");

        // connessione al DB

        // Puoi eseguire query qui utilizzando `connection`

        // Non dimenticare di chiudere la connessione alla fine
        // dbConnection.closeConnection();
    }

    public static synchronized ApplicationWindow getInstance() {
        if (instance == null) {
            instance = new ApplicationWindow();
        }
        return instance;
    }

    // Metodo per aggiornare il pannello attivo
    public void setPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }
}
