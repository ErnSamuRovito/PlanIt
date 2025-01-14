// Package: view

package view;

import model.factories.*;
import model.DesktopNotifier;
import model.UIComponentFactoryRegistry;
import view.views.LoginView;

import javax.swing.*;

public class ApplicationWindow {
    private static ApplicationWindow instance;
    private final JFrame frame;

    private static final int DEFAULT_HEIGHT = 600;
    private static final int DEFAULT_WIDTH = 800;
    private static final String DEFAULT_TITLE = "Plan-It";

    private ApplicationWindow() {
        frame = new JFrame(DEFAULT_TITLE);
        frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Imposta la posizione al centro dello schermo
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initializeFactories();

        // Imposta il pannello iniziale (LoginView)
        setPanel(new LoginView());

        DesktopNotifier notifier = new DesktopNotifier();
        notifier.sendNotification("Titolo della Notifica", "Questo è un messaggio di notifica");

    }

    public static synchronized ApplicationWindow getInstance() {
        if (instance == null) {
            instance = new ApplicationWindow();
        }
        return instance;
    }

    // Metodo per aggiornare il pannello attivo
    public void setPanel(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void initializeFactories()
    {
        UIComponentFactoryRegistry.getInstance().registerFactory("Button", new ButtonFactory());
        UIComponentFactoryRegistry.getInstance().registerFactory("TextField", new TextFieldFactory());
        UIComponentFactoryRegistry.getInstance().registerFactory("PasswordField", new PasswordFieldFactory());
        UIComponentFactoryRegistry.getInstance().registerFactory("Label", new LabelFactory());
        UIComponentFactoryRegistry.getInstance().registerFactory("DataPicker", new DataPickerFactory());
        UIComponentFactoryRegistry.getInstance().registerFactory("ComboBox", new ComboBoxFactory());
        UIComponentFactoryRegistry.getInstance().registerFactory("TextPane", new TextPaneFactory());
    }
}
