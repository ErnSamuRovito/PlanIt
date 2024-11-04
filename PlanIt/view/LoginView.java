// Package: view

package view;

import controller.LoginButtonController;
import view.factory.ButtonFactory;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {

    public LoginView() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new FlowLayout());

        // Usa la ButtonFactory per creare il bottone di login
        CustomButton loginButton = new ButtonFactory("Login")
                .setSize(new Dimension(100, 50))
                .setActionListener(new LoginButtonController())
                .create();

        // Aggiungi i componenti al pannello
        add(loginButton);
    }
}
