package view.panel.createPanel;

public abstract class CreatePanelDecorator extends CreatePanel {
    protected CreatePanel createPanel;

    public CreatePanelDecorator(CreatePanel createPanel) {
        this.createPanel = createPanel;
    }

    @Override
    public void buildPanel() {}
}

