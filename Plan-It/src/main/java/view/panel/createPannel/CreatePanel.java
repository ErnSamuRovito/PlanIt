package view.panel.createPannel;

import javax.swing.*;
import java.awt.*;

public class CreatePanel extends JPanel {
    public CreatePanel() {
        System.out.println("CreatePanel constructor called");
        buildPanel();
    }

    public void buildPanel() {
        // Add default components or layout settings
        setLayout(new FlowLayout());
    }
}
