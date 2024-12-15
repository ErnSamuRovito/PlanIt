package view.panel;

import core.GlobalResources;
import model.Plant.AvatarPlant;

import javax.swing.*;
import java.awt.*;

public class SideMenu extends JPanel {
    public SideMenu() {
        super();

        // Impostiamo il layout su BoxLayout verticale
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GlobalResources.COLOR_CREMA);
        setPreferredSize(new Dimension(200, 0)); // Larghezza fissa e altezza automatica

        // Aggiungi la GIF animata al side menu
        JLabel gifLabel = new JLabel(new ImageIcon(AvatarPlant.getInstance().getPathGifImage()));
        gifLabel.setAlignmentX(Component.CENTER_ALIGNMENT);  // Centra orizzontalmente il JLabel
        System.out.println("state pianta : " + AvatarPlant.getInstance().getState());
        System.out.println("path pianta : " + AvatarPlant.getInstance().getPathGifImage());
        add(gifLabel);

        // Aggiungi uno spazio che spinge la ClickableLabel in basso
        add(Box.createVerticalGlue());  // Questo spinge tutto verso il basso
    }
}
