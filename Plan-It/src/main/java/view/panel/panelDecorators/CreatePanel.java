package view.panel.panelDecorators;

import javax.swing.*;
import java.awt.*;

public class CreatePanel extends JPanel {
    public CreatePanel() {
        buildPanel();
    }

    public void buildPanel() {
        setLayout(new FlowLayout());
    }
}
