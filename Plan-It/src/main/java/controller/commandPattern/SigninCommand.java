package controller.commandPattern;

import model.FormatValidator;
import view.ApplicationWindow;
import view.UICreationalPattern.UIComponents.CustomPasswordField;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.DeskView;
import view.panel.SigninView;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class SigninCommand implements ActionCommand{
    private final SigninView parentView;

    public SigninCommand(SigninView parentView) {
        this.parentView = parentView;
    }

    @Override public void execute() {
        if (parentView != null) {
            // Ottieni i campi dalla vista
            CustomTextField emailField = parentView.getEmailField();
            String emailInput = emailField.getText();

            CustomPasswordField passwordField = parentView.getPasswordField();
            String passwordInput = passwordField.getPasswordString();

            // Validazione delle credenziali
            if (FormatValidator.isValidEmail(emailInput) && FormatValidator.isValidPassword(passwordInput)) {
                // Cambia il pannello
                ApplicationWindow.getInstance().setPanel(new DeskView());
            } else {
                // Mostra messaggi di errore
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
