package view.panel.createPanel;

import javax.swing.*;
import java.awt.*;

public class CreatePanel extends JPanel {
    public CreatePanel() {
        System.out.println("CreatePanel constructor called");
        buildPanel();
    }

    public void buildPanel() {
        setLayout(new FlowLayout());
    }
}
