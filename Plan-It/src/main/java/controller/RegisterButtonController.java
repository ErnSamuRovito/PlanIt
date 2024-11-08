package controller;

import model.EmailValidator;
import view.ApplicationWindow;
import view.panel.DeskView;
import view.panel.RegisterView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterButtonController implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        RegisterView registerView = (RegisterView) ((JButton) e.getSource()).getParent();

        String email = registerView.getEmail();
        if (!EmailValidator.isValid(email))
        {
            JOptionPane.showMessageDialog(registerView, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ApplicationWindow.getInstance().setPanel(new DeskView());
    }
}
