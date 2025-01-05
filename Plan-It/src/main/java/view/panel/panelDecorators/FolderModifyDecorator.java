package view.panel.panelDecorators;

//import controller.commandPattern.ModifyFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import view.UICreationalPattern.UIBuilders.*;
import view.UICreationalPattern.UIComponents.*;
import view.UICreationalPattern.UIFactories.*;

import java.awt.*;

public class FolderModifyDecorator extends CreatePanelDecorator {
    private static final Dimension FIELD_SIZE = new Dimension(200, 30);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

    private CustomTextField nameFolderField;
    private CustomButton modifyButton;

    private final String user, currFolder;

    public FolderModifyDecorator(CreatePanel createPanel, String user, String currFolder) {
        super(createPanel);
        this.user=user;
        this.currFolder=currFolder;
    }

    @Override
    public void buildPanel() {
        super.buildPanel();

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Creazione del campo per il nome della cartella
        UIBuilder nameFolderFieldBuilder = new CustomTextFieldBuilder();
        UIDirector.buildStandardTextField(nameFolderFieldBuilder);
        nameFolderFieldBuilder.size(FIELD_SIZE).placeholder("Insert folder name");

        UIComponentFactory textFieldFactory = new CustomTextFieldFactory(nameFolderFieldBuilder);
        nameFolderField = (CustomTextField) textFieldFactory.orderComponent(nameFolderFieldBuilder);

        // Creazione del pulsante per modificare la cartella
        UIBuilder buttonBuilder = new CustomButtonBuilder();
        UIDirector.buildStandardButton(buttonBuilder);
        buttonBuilder.text("Modify Folder")
                .size(BUTTON_SIZE);
                //.action(new ModifyFolderCommand(this));

        // Uso della factory per creare il pulsante
        UIComponentFactory buttonFactory = new CustomButtonFactory(buttonBuilder);
        modifyButton = (CustomButton) buttonFactory.orderComponent(buttonBuilder);

        // Creazione del link "Back"
        UIBuilder labelBuilder = new CustomLabelBuilder();
        UIDirector.buildBackClickableLabel(labelBuilder);
        labelBuilder.action(
                new GoToDeskViewCommand(ComponentManager.getInstance().getUser(), ComponentManager.getInstance().getCurrFolder())
        );
        UIComponentFactory labelFactory = new CustomLabelFactory(labelBuilder);
        CustomLabel backLabel = (CustomLabel) labelFactory.orderComponent(labelBuilder);

        // Aggiunta dei componenti al pannello
        gbc.gridy = 0; add(nameFolderField, gbc);
        gbc.gridy = 1; add(modifyButton, gbc);
        gbc.gridy = 2; add(backLabel, gbc);
    }

    public String getTextFieldName() {
        return nameFolderField.getText();
    }
}
