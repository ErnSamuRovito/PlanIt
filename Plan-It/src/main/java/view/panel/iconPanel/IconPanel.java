package view.panel.iconPanel;

import core.GlobalResources;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class IconPanel extends JPanel {
    protected BufferedImage image;
    protected String title;
    protected JLabel titleLabel;

    public IconPanel(String imagePath, String title) {
        try { image = ImageIO.read(new File(imagePath)); }
        catch (IOException e) { e.printStackTrace(); }

        setPreferredSize(new Dimension(
            image.getWidth()+GlobalResources.xOffset, image.getHeight()+GlobalResources.yOffset)
        );
        setBackground(new Color(255, 255, 255, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.title = title;
        addTitle();
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Disegna l'immagine centrata nel JPanel
            int x = (getWidth() - image.getWidth()) / 2;
            int y = (getHeight() - image.getHeight()) / 2;
            g.drawImage(image, x, y, this);
        }
    }
}
