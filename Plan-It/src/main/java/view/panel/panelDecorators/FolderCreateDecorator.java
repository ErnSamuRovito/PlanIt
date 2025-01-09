package view.panel.panelDecorators;

import controller.commandPattern.CreateFolderCommand;
import controller.commandPattern.navigationCommands.GoToDeskViewCommand;
import core.ComponentManager;
import core.GlobalResources;
import view.UICreationalPattern.UIComponents.CustomButton;
import view.UICreationalPattern.UIComponents.CustomLabel;
import view.UICreationalPattern.UIComponents.CustomTextField;
import view.panel.UIFactoryHelper;

import java.awt.*;

public class FolderCreateDecorator extends CreatePanelDecorator {
    private CustomTextField nameFolderField;
    private CustomButton createButton;
    private CustomLabel backLabel;

    public FolderCreateDecorator(CreatePanel createPanel) {
        super(createPanel);
    }

    @Override
    public void buildPanel() {
        super.buildPanel(); // Chiamata al metodo buildPanel della classe base

        setLayout(new GridBagLayout());
        setBackground(GlobalResources.COLOR_PANNA);

        nameFolderField = UIFactoryHelper.createTextField("", "Insert folder name");
        createButton = UIFactoryHelper.createButton("Create new folder", new CreateFolderCommand(this));
        backLabel = UIFactoryHelper.createClickableLabel("Back",
                new GoToDeskViewCommand(ComponentManager.getInstance().getUser(), ComponentManager.getInstance().getCurrFolder()));

        addComponentToPanel();
    }

    private void addComponentToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margini per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 0;
        add(nameFolderField, gbc);
        gbc.gridy = 1;
        add(createButton, gbc);
        gbc.gridy = 2;
        add(backLabel, gbc);
    }

    public String getTextFieldName() {
        return nameFolderField.getText();
    }
}
