package view.panel.iconPanel;

import controller.commandPattern.ActionCommand;
import core.GlobalResources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IconPanel extends JPanel {
    protected BufferedImage image;
    protected String title;
    protected JLabel titleLabel;
    protected ActionCommand command;

    public IconPanel(String title, String imagePath, ActionCommand command) {
        try { image = ImageIO.read(new File(imagePath)); }
        catch (IOException e) { e.printStackTrace(); }

        setPreferredSize(new Dimension(
            image.getWidth()+GlobalResources.xOffset, image.getHeight()+GlobalResources.yOffset)
        );
        setBackground(new Color(255, 255, 255, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.title = title;
        this.command = command;
        addTitle();
        initializeMouseListener();
    }

    public void initializeMouseListener() {
        //qui è possibile personalizzare ulteriormente il bottone...
        // Gestisci eventi di hover e click
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseClicked(MouseEvent e) { if (command != null) {command.execute();} }
        });
    }

    protected void addTitle() {
        titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT); // Centra la JLabel orizzontalmente

        // Aggiungi un filler per spingere la label verso il basso
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalGlue()); // Spazio elastico che spinge la label in basso
        verticalBox.add(titleLabel); // Aggiungi il titolo sotto
        add(verticalBox); // Aggiungi il box al pannello
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Disegna l'immagine centrata nel JPanel
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, this);
        }
    }
}
