package Controller;

import View.StWindow;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener
{
    private final StWindow stWindow;
    private final JPanel targetPanel;

    public ButtonController(StWindow stWindow, JPanel targetPanel)
    {
        this.stWindow = stWindow;
        this.targetPanel = targetPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        stWindow.setPanel(targetPanel);
    }
}
