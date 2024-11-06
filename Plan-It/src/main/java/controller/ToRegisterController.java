package controller;

import core.GlobalResources;
import view.ApplicationWindow;
import view.UI.CustomClickableLabel;
import view.panel.RegisterView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ToRegisterController implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        ApplicationWindow.getInstance().setPanel(new RegisterView());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Optional: Add behavior for when the mouse is pressed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Optional: Add behavior for when the mouse is released
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
