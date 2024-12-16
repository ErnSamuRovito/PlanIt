package view.panel;

import controller.commandPattern.ExploreFolderCommand;
import controller.commandPattern.GoBackCommand;
import controller.commandPattern.GoToLoginCommand;
import controller.commandPattern.GoToTaskViewCommand;
import core.ComponentManager;
import core.DatabaseFileLoader;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIBuilders.CustomLabelBuilder;
import view.UICreationalPattern.UIBuilders.UIDirector;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIFactories.CustomLabelFactory;
import view.UICreationalPattern.UIFactories.UIComponentFactory;
import view.panel.createPannel.CreatePanel;
import view.panel.createPannel.FolderCreateDecorator;
import view.panel.createPannel.TaskCreateDecorator;
import view.panel.iconPanel.IconFactory;
import view.panel.iconPanel.IconPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DeskView extends JPanel {
    private SplitPanel splitPanel;
    private IconPanel iconPanelAdd, iconPanelBack;
    private final String user, startFolder;

    public DeskView(String user, String startFolder) {
        this.user = user;
        this.startFolder = startFolder;

        // Imposta il layout principale
        setLayout(new BorderLayout());

        // Inizializza l'interfaccia utente
        initializeUI();
    }

    private void initializeUI() {
        // Crea e configura lo SplitPanel
        splitPanel = new SplitPanel();

        // Configura icone e dati
        addBackIcon();
        loadData();
        addCreateIcon();
        addPopupMenu();
        splitPanel.addBackClickableLabel(new GoToLoginCommand());

        // Aggiungi lo SplitPanel al centro del layout
        add(splitPanel, BorderLayout.CENTER);
    }

    private void loadData() {
        // Recupera i folder dal data provider e li aggiunge al pannello
        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            for (String folder : folderDAO.getFoldersByFolderAndUser(startFolder,user)){
                splitPanel.getHomePanel().add(
                        IconFactory.createIconPanel(
                                "folder", folder, new ExploreFolderCommand(user, folder)
                        )
                );
            }

            TaskDAOImpl taskDAO = new TaskDAOImpl(connection);
            for (String task : taskDAO.getTasksByFolderAndUser(startFolder,user)){
                splitPanel.getHomePanel().add(IconFactory.createIconPanel(
                        "task", task, new GoToTaskViewCommand(task, user, startFolder))
                );
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void addPopupMenu() {
        // Creazione del menu a comparsa
        JPopupMenu popupMenu = new JPopupMenu();

        // Creazione delle voci del menu per la creazione di folder o task
        JMenuItem createFolderItem = new JMenuItem("Create Folder");
        JMenuItem createTaskItem = new JMenuItem("Create Task");

        // Aggiungi le voci al menu a comparsa
        popupMenu.add(createFolderItem);
        popupMenu.add(createTaskItem);

        // Aggiungi gli action listener per ciascuna voce
        createFolderItem.addActionListener(e -> createFolder());
        createTaskItem.addActionListener(e -> createTask());

        // Imposta il menu a comparsa sul pannello home
        iconPanelAdd.setComponentPopupMenu(popupMenu);
    }

    protected void createTask() {
        CreatePanel createPanel = new CreatePanel();
        createPanel = new TaskCreateDecorator(createPanel);
        ComponentManager.getInstance().setPanel(createPanel);
    }

    protected void createFolder() {
        CreatePanel createPanel = new CreatePanel();
        createPanel = new FolderCreateDecorator(createPanel);
        ComponentManager.getInstance().setPanel(createPanel);
    }

    protected void addCreateIcon() {
        iconPanelAdd = IconFactory.createIconPanel("add", "new", null);
        splitPanel.getHomePanel().add(iconPanelAdd);
    }

    protected void addBackIcon() {
        if (!startFolder.equals("/root")) {
            iconPanelBack = IconFactory.createIconPanel(
                    "back",
                    "back",
                    new GoBackCommand(user, startFolder)
            );
            splitPanel.getHomePanel().add(iconPanelBack);
        }
    }
}
