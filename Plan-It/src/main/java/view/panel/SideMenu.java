package view.panel;

import core.GlobalResources;
import model.Plant.AvatarPlant;

import javax.swing.*;
import java.awt.*;

public class SideMenu extends JPanel{
    public SideMenu(){
        super();
        setBackground(GlobalResources.COLOR_CREMA);
        setPreferredSize(new Dimension(200, getHeight())); // Larghezza iniziale
        setLayout(new BorderLayout());

        // Aggiungi la GIF animata al side menu
        JLabel gifLabel = new JLabel(new ImageIcon(AvatarPlant.getInstance().getPathGifImage()));
        System.out.println("state pianta : " + AvatarPlant.getInstance().getState());
        System.out.println("path pianta : " + AvatarPlant.getInstance().getPathGifImage());
        add(gifLabel, BorderLayout.NORTH);
    }
}
