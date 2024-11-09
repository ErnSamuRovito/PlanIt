// Package: controller

package controller;

import view.ApplicationWindow;
import view.panel.SigninView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToSigninController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationWindow.getInstance().setPanel(new SigninView());
    }
}
