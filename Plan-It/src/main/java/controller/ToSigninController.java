package controller;

import model.FormatValidator;
import view.ApplicationWindow;
import view.panel.SigninView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToSigninController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationWindow.getInstance().setPanel(new SigninView());
    }
}
