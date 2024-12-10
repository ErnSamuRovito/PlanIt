// Package: core

package core;

import view.ApplicationWindow;
import view.panel.LoginView;
import view.panel.DeskView;
import view.panel.SigninView;

import javax.swing.*;

public class ComponentManager {
    private static ComponentManager instance;
    //private LoginView loginView;
    //private SigninView signinView;
    //private DeskView deskView;

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
        /*
        if (loginView == null) {loginView = new LoginView();}
        return loginView;
        */
        return new LoginView();
    }

    public SigninView getSigninView() {
        /*if (signinView == null) {signinView = new SigninView();}
        return signinView;
        */
        return new SigninView();
    }

    public DeskView getDeskView(String user, String startFolder) {
        /*
        if (deskView == null) {
            DataProvider dataProvider = new DatabaseDataProvider(user);
            deskView = new DeskView(dataProvider);
        }
        return deskView;
        */
        DataProvider dataProvider = new DatabaseDataProvider(user);
        dataProvider.setStartFolder(startFolder);
        return new DeskView(dataProvider);
    }

    // Metodo per impostare il pannello attivo tramite ApplicationWindow
    public void setPanel(JPanel panel) {
        ApplicationWindow.getInstance().setPanel(panel);
    }
}
