package controller;

import model.FormatValidator;
import view.ApplicationWindow;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.panel.DeskView;
import view.panel.SigninView;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class RegisterButtonController extends ControlAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof CustomButton registerButton) {

            SigninView parentView = (SigninView) SwingUtilities.getAncestorOfClass(SigninView.class, registerButton);

            if (parentView != null) {
                CustomTextField emailField = parentView.getEmailField();
                String emailInput = emailField.getText();

                CustomPasswordField passwordField = parentView.getPasswordField();
                String passwordInput = passwordField.getPasswordString();

                System.out.println(passwordInput);

                // Validate credentials
                if (FormatValidator.isValidEmail(emailInput) && FormatValidator.isValidPassword(passwordInput)) {
                    ApplicationWindow.getInstance().setPanel(new DeskView());
                } else {
                    if (!FormatValidator.isValidEmail(emailInput))
                        showMessageDialog(null, "Email isn't correct!", "Plan-It", ERROR_MESSAGE);
                    if (!FormatValidator.isValidPassword(passwordInput))
                        showMessageDialog(null, "The password must have at least 6 characters!", "Plan-It", ERROR_MESSAGE);
                    System.out.println("Email valida: " + FormatValidator.isValidEmail(emailInput));
                    System.out.println("Password valida: " + FormatValidator.isValidPassword(passwordInput));
                }
            }
        }
    }
}