package view.panel;

import controller.commandPattern.PasteComponentCommand;
import controller.commandPattern.navigationCommands.GoBackCommand;
import controller.commandPattern.navigationCommands.GoToLoginCommand;
import controller.controllers.DeskController;
import core.ComponentManager;
import model.plantStates.AvatarPlant;
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
        AvatarPlant.getInstance().subtractHP(AvatarPlant.getInstance().getPenance());

        initializeUI();
        new DeskController(this, user, startFolder);
    }

    private void initializeUI() {
        splitPanel = new SplitPanel();
        addBackIcon();
        splitPanel.addBackClickableLabel(new GoToLoginCommand());
        add(splitPanel, BorderLayout.CENTER);
    }

    public void addIconPanel(IconPanel iconPanel) {
        splitPanel.getHomePanel().add(iconPanel);
    }

    public void addPopupMenu() {
        // Creazione del menu a comparsa
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem createFolderItem = new JMenuItem("Create Folder");
        JMenuItem createTaskItem = new JMenuItem("Create Task");
        JMenuItem pasteComponent = new JMenuItem("Paste");

        popupMenu.add(createFolderItem);
        popupMenu.add(createTaskItem);
        createFolderItem.addActionListener(e -> createFolder());
        createTaskItem.addActionListener(e -> createTask());

        if (ComponentManager.getInstance().getCuttedComponentId()!=null && ComponentManager.getInstance().getCuttedComponentType()!=null) {
            popupMenu.add(pasteComponent);
            pasteComponent.addActionListener(e -> new PasteComponentCommand().execute());
        }
        iconPanelAdd.setComponentPopupMenu(popupMenu);
    }

    protected void createTask() {
        TaskCreateView taskCreateView = new TaskCreateView();
        ComponentManager.getInstance().setPanel(taskCreateView);
    }

    protected void createFolder() {
        FolderCreateView createFolderPanel = new FolderCreateView();
        ComponentManager.getInstance().setPanel(createFolderPanel);
    }

    public void addCreateIcon() {
        iconPanelAdd = IconFactory.createIconPanel("add", "new", null);
        splitPanel.getHomePanel().add(iconPanelAdd);
    }

    protected void addBackIcon() {
        if (!startFolder.equals("/root")) {
            iconPanelBack = IconFactory.createIconPanel(
                    "back", "back", new GoBackCommand()
            );
            splitPanel.getHomePanel().add(iconPanelBack);
        }
    }
}
