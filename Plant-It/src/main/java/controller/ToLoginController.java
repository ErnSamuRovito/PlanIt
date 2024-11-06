package controller;

import core.GlobalResources;
import view.ApplicationWindow;
import view.UI.CustomClickableLabel;
import view.panel.LoginView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToLoginController implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        ApplicationWindow.getInstance().setPanel(new LoginView());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof CustomClickableLabel label) {
            label.setForeground(GlobalResources.COLOR_GREEN_2);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof CustomClickableLabel label) {
            label.setForeground(GlobalResources.COLOR_GREEN_1);
        }
    }
}
