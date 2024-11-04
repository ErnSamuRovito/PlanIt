// Package: core

package core;

import view.LoginView;
import view.DeskView;
import view.ApplicationWindow;

import javax.swing.*;

public class ComponentManager {
    private static ComponentManager instance;
    private LoginView loginView;
    private DeskView mainView;

    private ComponentManager() {
        // Costruttore privato per il singleton
    }

    public static synchronized ComponentManager getInstance() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }

    // Metodo per ottenere o creare LoginView
    public LoginView getLoginView() {
        if (loginView == null) {
            loginView = new LoginView();
        }
        return loginView;
    }

    // Metodo per ottenere o creare DeskView
    public DeskView getMainView() {
        if (mainView == null) {
            mainView = new DeskView();
        }
        return mainView;
    }

    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {
        ApplicationWindow.getInstance().setPanel(panel);
    }
}
