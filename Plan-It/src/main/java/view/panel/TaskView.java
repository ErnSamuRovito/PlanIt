package view.panel;

import controller.commandPattern.GoToDeskViewCommand;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextPane;
import view.UICreationalPattern.UIComponents.UIComponent;
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
    private final String title,user,startFolder;
    private final JPanel homePanel;

    public TaskView(String title, String user, String startFolder) {
        this.title = title;
        this.user = user;
        this.startFolder = startFolder;

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
        showData();
    }

    private void showData() {
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            ArrayList<String> result = taskDAO.getTaskDataByTitleAndFolderAndUsername(title, startFolder, user);

            if (!result.isEmpty()) {
                createComponent(result);
            }
        } catch (SQLException e) {throw new RuntimeException(e);}
    }

    private void createComponent(ArrayList<String> result){
        // Configura il layout per il pannello "HomePanel" dello SplitPanel
        GridBagConstraints gbc = setGridBagConstraints();

        //CREAZIONE DELLA LABEL CONTENENTE IL TITOLO DEL TASK
        UIBuilder titleLabelBuilder = new CustomLabelBuilder();
        UIDirector.buildStandardLabel(titleLabelBuilder);
        titleLabelBuilder.text(result.get(0)); // Imposta il testo del titolo
        UIComponentFactory titleLabelFactory = new CustomLabelFactory(titleLabelBuilder);
        CustomLabel titleLabel = (CustomLabel) titleLabelFactory.orderComponent(titleLabelBuilder);

        //CREAZIONE DEL PANNELLO CONTENENTE LA DESCRIZIONE DEL TASK
        CustomTextPaneBuilder textPaneBuilder = new CustomTextPaneBuilder();
        UIDirector.buildStandardTextPane(textPaneBuilder);
        textPaneBuilder.content(result.get(1));
        UIComponentFactory textPaneFactory = new CustomTextPaneFactory(textPaneBuilder);
        CustomTextPane customTextPane = (CustomTextPane) textPaneFactory.createComponent();

        // Aggiungi il titolo al pannello
        gbc.gridy = 0;
        homePanel.add(titleLabel, gbc);

        // Avvolgi il JTextPane in un JScrollPane
        JScrollPane scrollPane = new JScrollPane(customTextPane);
        int verticalDim = splitPanel.getHeight() / 2;
        scrollPane.setPreferredSize(new Dimension(0, verticalDim));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Aggiungi lo JScrollPane al pannello
        gbc.gridy = 1;
        gbc.weighty = 0.5; // Assegna il 50% dello spazio verticale
        gbc.fill = GridBagConstraints.BOTH; // Permette al componente di espandersi in entrambe le direzioni
        homePanel.add(scrollPane, gbc);

        // Aggiungi un riempitivo per spingere i componenti verso l'alto
        gbc.gridy = 2;
        gbc.weighty = 0.5; // Il riempitivo occupa il restante 50% dello spazio
        homePanel.add(Box.createVerticalGlue(), gbc);
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
