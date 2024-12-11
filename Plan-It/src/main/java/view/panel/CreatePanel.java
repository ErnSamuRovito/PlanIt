package view.panel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

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
