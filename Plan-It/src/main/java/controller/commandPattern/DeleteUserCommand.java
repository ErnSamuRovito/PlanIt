package controller.commandPattern;

import core.ComponentManager;
import core.SqLiteConnection;
import model.dao.user.UserDAO;
import model.dao.user.UserDAOImpl;
import view.panel.LoginView;
import javax.swing.JOptionPane;

public class DeleteUserCommand implements ActionCommand {
    private int userId;

    public DeleteUserCommand(int userId) {
        this.userId = userId;
    }

    @Override
    public void execute() {
        // Finestra di conferma prima di procedere con l'eliminazione
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        // Se l'utente conferma l'eliminazione
        if (confirm == JOptionPane.YES_OPTION) {
            UserDAO userDAO = new UserDAOImpl(SqLiteConnection.getInstance().getConnection());
            boolean success = userDAO.deleteUser(userId);

            // Se l'eliminazione è andata a buon fine
            if (success) {
                JOptionPane.showMessageDialog(null, "Your account has been deleted successfully.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                // Ritorna alla schermata di login
                ComponentManager.getInstance().setPanel(new LoginView());
            } else {
                JOptionPane.showMessageDialog(null, "An error occurred while trying to delete your account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // L'utente ha annullato l'eliminazione
            JOptionPane.showMessageDialog(null, "Account deletion canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
