package view.panel.createPannel;

public abstract class CreatePanelDecorator extends CreatePanel {
    protected CreatePanel createPanel;

    public CreatePanelDecorator(CreatePanel createPanel) {
        this.createPanel = createPanel;
    }

    @Override
    public void buildPanel() {
        //createPanel.buildPanel();  // Calls the buildPanel of the original CreatePanel
    }
}

