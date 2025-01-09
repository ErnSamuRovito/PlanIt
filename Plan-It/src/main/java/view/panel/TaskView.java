package view.panel;

import controller.commandPattern.TaskDoneCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextPane;
import view.UICreationalPattern.UIFactories.CustomButtonFactory;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.CustomTextPaneFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskView extends JPanel {
    private final SplitPanel splitPanel;
    private final String title, user, startFolder;
    private final JPanel homePanel;
    private CustomButton doneButton;
    private int taskState;

    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    public TaskView(String title, String user, String startFolder) {
        this.title = title;
        this.user = user;
        this.startFolder = startFolder;

        // Controlla lo stato del task.
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            taskState = taskDAO.checkTaskByFolderAndTitle(startFolder, user, title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Imposta il layout principale per TaskView
        setLayout(new BorderLayout());

        // Inizializza lo SplitPanel e aggiungilo al centro
        splitPanel = new SplitPanel();
        homePanel = splitPanel.getHomePanel();
        homePanel.setLayout(new GridBagLayout());
        homePanel.setBackground(GlobalResources.COLOR_PANNA);

        add(splitPanel, BorderLayout.CENTER);

        splitPanel.addBackClickableLabel(new GoToDeskViewCommand(user, startFolder));

        // Mostra i dati nel pannello
        showTaskData();
    }

    private void showTaskData() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            ArrayList<String> result = taskDAO.getTaskDataByTitleAndFolderAndUsername(title, startFolder, user);

            if (!result.isEmpty()) {
                createComponent(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createComponent(ArrayList<String> result) {
        // Configura il layout per il pannello "HomePanel" dello SplitPanel
        GridBagConstraints gbc = setGridBagConstraints();

        // CREAZIONE DELLA LABEL CONTENENTE IL TITOLO DEL TASK
        UIBuilder titleLabelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(titleLabelBuilder);
        titleLabelBuilder.text(result.get(1)); // Imposta il testo del titolo
        UIComponentFactory titleLabelFactory = new CustomLabelFactory(titleLabelBuilder);
        CustomLabel titleLabel = (CustomLabel) titleLabelFactory.orderComponent(titleLabelBuilder);

        // CREAZIONE DEL PANNELLO CONTENENTE LA DESCRIZIONE DEL TASK
        CustomTextPaneBuilder textPaneBuilder = new CustomTextPaneBuilder();
        UIDirector.buildStandardTextPane(textPaneBuilder);
        textPaneBuilder.content(result.get(2)); // Imposta la descrizione
        UIComponentFactory textPaneFactory = new CustomTextPaneFactory(textPaneBuilder);
        CustomTextPane customTextPane = (CustomTextPane) textPaneFactory.createComponent();

        // Creazione del pulsante DONE usando il Builder e la Factory
        if (taskState != -1 && taskState != 100) {
            UIBuilder buttonBuilder = new CustomButtonBuilder();
            UIDirector.buildStandardButton(buttonBuilder);
            buttonBuilder.text("DONE!").size(BUTTON_SIZE).action(new TaskDoneCommand(result.get(0)));
            // Usa la factory per creare il pulsante
            UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
            doneButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);
        }

        // Aggiungi il titolo al pannello
        gbc.gridy = 0;
        homePanel.add(titleLabel, gbc);

        // Avvolgi il JTextPane in un JScrollPane
        JScrollPane scrollPane = new JScrollPane(customTextPane);
        scrollPane.setPreferredSize(new Dimension(0, 350)); // Imposta una dimensione fissa per l'immagine
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Aggiungi lo JScrollPane al pannello
        gbc.gridy = 1;
        gbc.weighty = 0.6; // Assegna il 60% dello spazio verticale
        gbc.fill = GridBagConstraints.BOTH; // Permette al componente di espandersi in entrambe le direzioni
        homePanel.add(scrollPane, gbc);

        // Aggiungi un riempitivo per spingere i componenti verso l'alto
        gbc.gridy = 2;
        gbc.weighty = 0.1; // Il riempitivo occupa il restante 10% dello spazio
        homePanel.add(Box.createVerticalGlue(), gbc);

        // Aggiungi il pulsante Done se necessario
        if (taskState != -1 && taskState != 100) {
            gbc.gridy = 3;
            gbc.weighty = 0.2; // Assegna il 20% dello spazio verticale
            homePanel.add(doneButton, gbc);
        }
    }

    public GridBagConstraints setGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; // Orienta i componenti verso l'alto
        gbc.weightx = 1.0; // Permette ai componenti di espandersi orizzontalmente
        gbc.weighty = 0; // Evita di espandere verticalmente i componenti
        return gbc;
    }
}
