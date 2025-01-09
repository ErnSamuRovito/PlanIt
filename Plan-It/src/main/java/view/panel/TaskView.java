package view.panel;

import controller.commandPattern.TaskDoneCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextPane;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskView extends JPanel {
    private SplitPanel splitPanel;
    private final String title, user, startFolder;
    private JPanel homePanel;
    private CustomButton doneButton;
    private int taskState;

    public TaskView(String title, String user, String startFolder) {
        this.title = title;
        this.user = user;
        this.startFolder = startFolder;

        // Controlla lo stato del task.
        initializeTaskState();

        // Imposta il layout principale per TaskView
        setupMainLayout();

        // Mostra i dati nel pannello
        showTaskData();
    }

    private void initializeTaskState() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            taskState = taskDAO.checkTaskByFolderAndTitle(startFolder, user, title);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupMainLayout() {
        setLayout(new BorderLayout());

        // Inizializza lo SplitPanel e aggiungilo al centro
        splitPanel = new SplitPanel();
        homePanel = splitPanel.getHomePanel();
        homePanel.setLayout(new GridBagLayout());
        homePanel.setBackground(GlobalResources.COLOR_PANNA);

        add(splitPanel, BorderLayout.CENTER);

        // Aggiungi il link per tornare alla vista principale
        splitPanel.addBackClickableLabel(new GoToDeskViewCommand(user, startFolder));
    }

    private void showTaskData() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            ArrayList<String> result = taskDAO.getTaskDataByTitleAndFolderAndUsername(title, startFolder, user);

            if (!result.isEmpty()) {
                createTaskComponents(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTaskComponents(ArrayList<String> result) {
        // Configura il layout per il pannello "HomePanel"
        GridBagConstraints gbc = setGridBagConstraints();

        // Crea e aggiungi la label del titolo
        CustomLabel titleLabel = UIFactoryHelper.createLabel(result.get(1));
        addComponentToPanel(titleLabel, gbc, 0);

        // Crea e aggiungi il pannello di descrizione
        CustomTextPane customTextPane = UIFactoryHelper.createTextPane(result.get(2));
        JScrollPane scrollPane = UIFactoryHelper.createScrollPane(customTextPane, new Dimension(0, 350));
        addComponentToPanel(scrollPane, gbc, 1, 0.6);

        // Aggiungi un riempitivo per spingere i componenti verso l'alto
        gbc.gridy = 2;
        gbc.weighty = 0.1;
        homePanel.add(Box.createVerticalGlue(), gbc);

        // Se il task non è ancora completato, aggiungi il pulsante DONE
        if (taskState != -1 && taskState != 100) {
            doneButton = UIFactoryHelper.createButton("DONE!", new TaskDoneCommand(result.get(0)));
            addComponentToPanel(doneButton, gbc, 3, 0.2);
        }
    }

    private void addComponentToPanel(Component component, GridBagConstraints gbc, int gridY) {
        addComponentToPanel(component, gbc, gridY, 0);
    }

    private void addComponentToPanel(Component component, GridBagConstraints gbc, int gridY, double weightY) {
        gbc.gridy = gridY;
        gbc.weighty = weightY;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        homePanel.add(component, gbc);
    }

    private GridBagConstraints setGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH; // Orienta i componenti verso l'alto
        gbc.weightx = 1.0; // Permette ai componenti di espandersi orizzontalmente
        gbc.weighty = 0; // Evita di espandere verticalmente i componenti
        return gbc;
    }
}
