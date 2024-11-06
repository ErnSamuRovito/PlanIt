// Package: controller

package controller;

import view.ApplicationWindow;
import view.panel.DeskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginButtonController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationWindow.getInstance().setPanel(new DeskView());
    }
}
