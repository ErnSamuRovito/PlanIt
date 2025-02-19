package view.panel.iconPanel;

import controller.commandPattern.ActionCommand;
import controller.commandPattern.DeleteComponentCommand;
import controller.commandPattern.navigationCommands.GoToModifyFolderCommand;
import controller.commandPattern.navigationCommands.GoToModifyTaskCommand;
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
    protected String title, imagePath;
    protected JLabel titleLabel;
    protected ActionCommand command;

    public IconPanel(String title, String imagePath, ActionCommand command) {
        this.imagePath = imagePath;
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

    private JPopupMenu createContextMenu(){
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        // Aggiungi azioni ai menu item
        if (imagePath.equals(GlobalResources.folderImage))
            editItem.addActionListener(e -> new GoToModifyFolderCommand(title).execute());
        else
            editItem.addActionListener(e -> new GoToModifyTaskCommand(title).execute());
        deleteItem.addActionListener(e -> new DeleteComponentCommand(title,imagePath).execute()); //imagePath serve a capire se è una cartella o un task

        contextMenu.add(editItem);
        contextMenu.add(deleteItem);

        return contextMenu;
    }

    public void initializeMouseListener() {
        JPopupMenu contextMenu=createContextMenu();

        // Gestisci eventi di hover e click
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Esegui il comando se il tasto sinistro è cliccato
                    if (command != null) {
                        command.execute();
                    }
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    // Mostra il menù contestuale per il clic destro se è una un folder o un task.
                    if (!imagePath.equals(GlobalResources.backImage) && !imagePath.equals(GlobalResources.addImage))
                        contextMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
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
