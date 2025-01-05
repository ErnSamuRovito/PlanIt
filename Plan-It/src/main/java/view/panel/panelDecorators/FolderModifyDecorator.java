package view.panel.panelDecorators;

import controller.commandPattern.ModifyFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import core.SqLiteConnection;
import model.dao.folder.FolderDAOImpl;
import model.dao.task.TaskDAOImpl;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class FolderModifyDecorator extends CreatePanelDecorator {

    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField nameFolderField;
    private CustomButton modifyButton;
    private final String user;
    private final String folder;

    private int id;
    private String title;

    public FolderModifyDecorator(CreatePanel createPanel, String user, String folder) {
        super(createPanel);
        this.user = user;
        this.folder = folder;

        setupPanelLayout();
        loadFolderData();
        addComponentsToPanel();
    }

    @Override
    public void buildPanel() {
        super.buildPanel();
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
            id = folderDAO.getFolderIdByNameAndOwner(folder,user);

            title=folderDAO.getFolderById(id).getFolderName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //aggiungi i componenti al pannello
    private void addComponentsToPanel() {
        GridBagConstraints gbc = createGridBagConstraints();

        // Add folder name field to the panel
        nameFolderField = createNameFolderField();
        gbc.gridy = 0;
        add(nameFolderField, gbc);

        // Add modify folder button to the panel
        modifyButton = createModifyButton();
        gbc.gridy = 1;
        add(modifyButton, gbc);

        // Add back label to the panel
        CustomLabel backLabel = createBackLabel();
        gbc.gridy = 2;
        add(backLabel, gbc);
    }

    // grid bag
    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margins for the components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    // text field per il nome della cartella
    private CustomTextField createNameFolderField() {
        UIBuilder nameFolderFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(nameFolderFieldBuilder);
        nameFolderFieldBuilder.text(title).placeholder("").size(FIELD_SIZE);

        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(nameFolderFieldBuilder);
        return (CustomTextField) textFieldFactory.orderComponent(nameFolderFieldBuilder);
    }

    // crea il bottone per modificare il folder
    private CustomButton createModifyButton() {
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Modify Folder")
                .size(BUTTON_SIZE)
                .action(new ModifyFolderCommand(this));

        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        return (CustomButton) buttonFactory.orderComponent(buttonBuilder);
    }

    // crea il bottone per tornare indietro
    private CustomLabel createBackLabel() {
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(
                new GoToDeskViewCommand(ComponentManager.getInstance().getUser(), ComponentManager.getInstance().getCurrFolder())
        );

        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        return (CustomLabel) labelFactory.orderComponent(labelBuilder);
    }

    public String getTextFieldName() {
        return nameFolderField.getText();
    }
    public int getFolderId(){return id;}
}
