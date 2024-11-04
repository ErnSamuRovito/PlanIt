package View;

import javax.swing.*;
import java.awt.*;
import Controller.ButtonController;

public class LoginPanel extends JPanel {

    public LoginPanel(StWindow stWindow)
    {
        setBackground(Color.ORANGE);

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 50));
        add(loginButton);

        loginButton.addActionListener(new ButtonController(stWindow, new MainPanel()));
    }
}
