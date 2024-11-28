// Package: controller

package controller;

import view.ApplicationWindow;
import view.panel.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToLoginController extends ControlAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        ApplicationWindow.getInstance().setPanel(new LoginView());
    }
}
