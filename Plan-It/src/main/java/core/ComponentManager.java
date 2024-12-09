// Package: core

package core;

import view.ApplicationWindow;
import view.panel.LoginView;
import view.panel.DeskView;
import view.panel.SigninView;

import javax.swing.*;

public class ComponentManager {
    private static ComponentManager instance;
    private LoginView loginView;
    private SigninView signinView;
    private DeskView deskView;

    private ComponentManager() {
        // Costruttore privato per il singleton
    }

    public static synchronized ComponentManager getInstance() {
        if (instance == null) {
            instance = new ComponentManager();
        }
        return instance;
    }

    public LoginView getLoginView() {
        if (loginView == null) {
            loginView = new LoginView();
        }
        return loginView;
    }

    public SigninView getSigninView() {
        if (signinView == null) {
            signinView = new SigninView();
        }
        return signinView;
    }

    public DeskView getDeskView(String user) {
        if (deskView == null) {
            DataProvider dataProvider = new DatabaseDataProvider(user);
            deskView = new DeskView(dataProvider);
        }
        return deskView;
    }

    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {
        ApplicationWindow.getInstance().setPanel(panel);
    }
}
