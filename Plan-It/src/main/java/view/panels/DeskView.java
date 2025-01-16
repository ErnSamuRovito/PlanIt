package view.panels;

import controller.commandPattern.componentCommands.PasteComponentCommand;
import controller.commandPattern.navigationCommands.GoBackCommand;
import controller.commandPattern.navigationCommands.GoToLoginCommand;
import controller.controllers.DeskController;
import core.ComponentManager;
import model.plantStates.AvatarPlant;
import view.helperPanels.SplitPanel;
import view.iconPanel.IconFactory;
import view.iconPanel.IconPanel;

import javax.swing.*;
import java.awt.*;

public class DeskView extends JPanel {
    private SplitPanel splitPanel;
    private IconPanel iconPanelAdd, iconPanelBack;
    private final String user;
    private final String startFolder;

    public DeskView(String user, String startFolder) {
        this.user = user;
        this.startFolder = startFolder;

        setLayout(new BorderLayout());
        handleAvatarPenance();

        initializeUI();
        initializeController();
    }

    // Sezione: Inizializzazione
    private void handleAvatarPenance() {
        AvatarPlant avatarPlant = AvatarPlant.getInstance();
        //System.out.println("PENALITA': "+avatarPlant.getPenance());
        avatarPlant.subtractHP(avatarPlant.getPenance());
    }

    private void initializeUI() {
        splitPanel = new SplitPanel();
        addBackIcon();
        splitPanel.addBackClickableLabel(new GoToLoginCommand());
        add(splitPanel, BorderLayout.CENTER);
    }

    private void initializeController() {
        new DeskController(this, user, startFolder);
    }

    // Sezione: Gestione IconPanel
    public void addIconPanel(IconPanel iconPanel) {
        splitPanel.getHomePanel().add(iconPanel);
    }

    public void addCreateIcon() {
        iconPanelAdd = IconFactory.createIconPanel("add", "new", null);
        splitPanel.getHomePanel().add(iconPanelAdd);
    }

    protected void addBackIcon() {
        if (!isRootFolder()) {
            iconPanelBack = IconFactory.createIconPanel("back", "back", new GoBackCommand());
            splitPanel.getHomePanel().add(iconPanelBack);
        }
    }

    private boolean isRootFolder() {
        return "/root".equals(startFolder);
    }

    // Sezione: Gestione Popup Menu
    public void addPopupMenu() {
        JPopupMenu popupMenu = createPopupMenu();
        iconPanelAdd.setComponentPopupMenu(popupMenu);
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        // Aggiunta opzioni principali
        addMenuItem(popupMenu, "Create Folder", e -> createFolder());
        addMenuItem(popupMenu, "Create Task", e -> createTask());

        // Aggiunta opzione "Paste" se applicabile
        if (isPasteAvailable()) {
            addMenuItem(popupMenu, "Paste", e -> new PasteComponentCommand().execute());
        }
        return popupMenu;
    }

    private void addMenuItem(JPopupMenu menu, String label, java.awt.event.ActionListener action) {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.addActionListener(action);
        menu.add(menuItem);
    }

    private boolean isPasteAvailable() {
        ComponentManager componentManager = ComponentManager.getInstance();
        return componentManager.getCuttedComponentId() != null &&
                componentManager.getCuttedComponentType() != null;
    }

    // Sezione: Azioni di creazione
    protected void createFolder() {
        FolderCreateView createFolderPanel = new FolderCreateView();
        ComponentManager.getInstance().setPanel(createFolderPanel);
    }

    protected void createTask() {
        TaskCreateView taskCreateView = new TaskCreateView();
        ComponentManager.getInstance().setPanel(taskCreateView);
    }
}
