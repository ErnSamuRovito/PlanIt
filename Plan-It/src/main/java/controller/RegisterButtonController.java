package controller;

import model.FormatValidator;
import view.ApplicationWindow;
import view.panel.DeskView;
import view.panel.SigninView;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class RegisterButtonController implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof CustomButton registerButton) {

            SigninView parentView = (SigninView) SwingUtilities.getAncestorOfClass(SigninView.class, registerButton);

            if (parentView != null)
            {
                CustomTextField emailField = parentView.getEmailField();
                String emailInput = emailField.getText();

                // Validate the email
                if (FormatValidator.isValidEmail(emailInput)) {
                    ApplicationWindow.getInstance().setPanel(new DeskView());
                } else {
                    showMessageDialog(null, "Email isn't correct!", "Plan-It", ERROR_MESSAGE);
                    System.out.println("Email valida: " + FormatValidator.isValidEmail(emailInput));
                }
            }
        }
    }
}