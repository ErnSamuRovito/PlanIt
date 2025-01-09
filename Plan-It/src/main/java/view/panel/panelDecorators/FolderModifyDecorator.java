package view.panel.panelDecorators;

import controller.commandPattern.ModifyFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.UIFactoryHelper;

import java.awt.*;
import java.sql.Connection;

public class FolderModifyDecorator extends CreatePanelDecorator {
    private CustomTextField nameFolderField;
    private CustomButton modifyButton;
    private CustomLabel backLabel;
    private final String user;
    private final String folder;

    private int id;
    private String title;

    public FolderModifyDecorator(CreatePanel createPanel, String user, String folder) {
        super(createPanel);
        this.user = user;
        this.folder = folder;
        loadFolderData();
        createComponents();
        addComponentsToPanel();
    }

    @Override
    public void buildPanel() {
        super.buildPanel();
        setupPanelLayout();
    }

    // imposta il layout
    private void setupPanelLayout() {
        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);
    }

    private void loadFolderData() {
        if ((folder == null || folder.isEmpty()) || (user == null || user.isEmpty())) {
            return;
        }

        try (Connection connection = SqLiteConnection.getInstance().getConnection()) {
            FolderDAOImpl folderDAO = new FolderDAOImpl(connection);
            id = folderDAO.getFolderIdByNameAndOwner(folder, user);
            title = folderDAO.getFolderById(id).getFolderName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createComponents() {
        nameFolderField = UIFactoryHelper.createTextField(title, "");
        modifyButton = UIFactoryHelper.createButton("Modify Folder", new ModifyFolderCommand(this));
        backLabel = UIFactoryHelper.createClickableLabel("Back",
                new GoToDeskViewCommand(ComponentManager.getInstance().getUser(), ComponentManager.getInstance().getCurrFolder()));
    }

    // aggiungi i componenti al pannello
    private void addComponentsToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Aggiungi il campo di testo per il nome della cartella
        gbc.gridy = 0;
        add(nameFolderField, gbc);

        // Aggiungi il pulsante per modificare la cartella
        gbc.gridy = 1;
        add(modifyButton, gbc);

        // Aggiungi il link per tornare indietro
        gbc.gridy = 2;
        add(backLabel, gbc);
    }

    public String getTextFieldName() {
        return nameFolderField.getText();
    }

    public int getFolderId() {
        return id;
    }
}
